package com.balance.service.user;


import com.balance.config.JwtProvider;
import com.balance.model.User;
import com.balance.model.UserDetailsInfo;
import com.balance.model.VerificationToken;
import com.balance.model.dto.LoginRequest;
import com.balance.repository.UserRepository;
import com.balance.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
@Transactional
public class AuthenticationService {
   private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
   private final UserRepository userRepository;
   private final AuthenticationManager authenticationManager;
   private final JwtProvider jwtProvider;
   private final VerificationTokenRepository verificationTokenRepository;

   public AuthenticationService(UserRepository userRepository,
                                AuthenticationManager authenticationManager,
                                JwtProvider jwtProvider,
                                VerificationTokenRepository verificationTokenRepository) {
      this.userRepository = userRepository;
      this.authenticationManager = authenticationManager;
      this.jwtProvider = jwtProvider;
      this.verificationTokenRepository = verificationTokenRepository;
   }

   public User verifyTokenAndActiveUser(String token) {
      VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenByToken(token).orElseThrow(RuntimeException::new);
      User user = verifyToken(verificationToken);
      user.setActive(true);
      log.info("User {} is activated", user.getUsername());
      return user;
   }

   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public User activateUser(String username, boolean isActive) {
      return setActive(username, isActive);
   }

   public String login(LoginRequest loginRequest) {
      Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authenticate.getPrincipal();
      return jwtProvider.generateToken(userDetailsInfo.getUsername());
   }

   public UserDetails getCurrentPrincipal() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      return (UserDetails) authentication.getPrincipal();
   }

   public VerificationToken createVerificationToken(User user) {
      //String token = UUID.randomUUID().toString();
	  String token = new DecimalFormat("000000").format(new Random().nextInt(999999));
      VerificationToken verificationToken = new VerificationToken();
      verificationToken.setToken(token);
      verificationToken.setExpiration(new Date());
      verificationToken.setUser(user);
      verificationTokenRepository.save(verificationToken);
      return verificationToken;
   }

   public User signup(User user) {
      return userRepository.save(user);
   }

   private User setActive(String username, boolean isActive) {
      User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(username));
      user.setActive(isActive);
      return user;
   }

   private User verifyToken(VerificationToken verificationToken) {
      Date activationDate = new Date();
      if (activationDate.before(verificationToken.getStartDate()) || activationDate.after(verificationToken.getEndDate())) {
         throw new RuntimeException();
      }
      return verificationToken.getUser();
   }

}
