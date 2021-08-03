
package com.balance.config;

import com.balance.service.user.UserDetailsInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   private final UserDetailsInfoService userDetailsInfoService;
   private final JwtFilter jwtFilter;

   public SecurityConfiguration(UserDetailsInfoService userDetailsInfoService, JwtFilter jwtFilter) {
      this.userDetailsInfoService = userDetailsInfoService;
      this.jwtFilter = jwtFilter;
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsInfoService);
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   private static final String[] AUTH_WHITELIST = {
         "/swagger-resources/**",
         "/swagger-ui.html",
         "/v2/api-docs",
         "/webjars/**"
   };

   @Override
   public void configure(WebSecurity web) {
      web.ignoring().antMatchers(AUTH_WHITELIST);
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.httpBasic().disable()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/api/signup", "/api/login", "/api/activate", "/api/resendToken").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      ;
   }
}
