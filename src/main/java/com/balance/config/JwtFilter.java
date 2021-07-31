package com.balance.config;

import com.balance.service.user.UserDetailsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

   public static final String AUTHORIZATION = "Authorization";
   private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

   private final JwtProvider jwtProvider;

   private final UserDetailsInfoService userDetailsInfoService;

   public JwtFilter(JwtProvider jwtProvider, UserDetailsInfoService userDetailsInfoService) {
      this.jwtProvider = jwtProvider;
      this.userDetailsInfoService = userDetailsInfoService;
   }

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      String token = getTokenFromRequest((HttpServletRequest) servletRequest);
      if (token != null && jwtProvider.validateToken(token)) {
         String userLogin = jwtProvider.getLoginFromToken(token);
         UserDetails detailsInfo = userDetailsInfoService.loadUserByUsername(userLogin);
         UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(detailsInfo, null, detailsInfo.getAuthorities());
         SecurityContextHolder.getContext().setAuthentication(auth);
      }
      filterChain.doFilter(servletRequest, servletResponse);
   }

   private String getTokenFromRequest(HttpServletRequest request) {
      String bearer = request.getHeader(AUTHORIZATION);
      if (hasText(bearer) && bearer.startsWith("Bearer ")) {
         return bearer.substring(7);
      }
      return null;
   }
}