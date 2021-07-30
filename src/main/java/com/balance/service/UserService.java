package com.balance.service;

import com.balance.config.JwtProvider;
import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.UserDetailsInfo;
import com.balance.model.dto.*;
import com.balance.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class UserService {

   private static final String ROLE_USER = "ROLE_USER";
   private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
   private final UserRepository userRepository;
   private final AuthenticationManager authenticationManager;
   private final PasswordEncoder passwordEncoder;
   private final JwtProvider jwtProvider;

   public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
      this.userRepository = userRepository;
      this.authenticationManager = authenticationManager;
      this.passwordEncoder = passwordEncoder;
      this.jwtProvider = jwtProvider;
   }

   public LoginResponse login(LoginRequest loginRequest) {
      Authentication authenticate = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authenticate.getPrincipal();
      String token = jwtProvider.generateToken(userDetailsInfo.getUsername());
      return new LoginResponse(token);
   }

   public void signup(SignupRequest signupRequest) {
      User user = new User();
      user.setUsername(signupRequest.getUsername());
      user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
      user.setFirstName(signupRequest.getFirstName());
      user.setLastName(signupRequest.getLastName());
      String roles = signupRequest.getRoles();
      if (isEmpty(roles)) {
         roles = ROLE_USER;
      }
      user.setRoles(roles);

      try {
         userRepository.save(user);
      } catch (Exception e) {
         log.error("Failed to signup a user {}", e.getMessage());
      }
   }

   public UserDTO signupCommunityToUser(Long userId, Community community) {
      User user = userRepository.getById(userId);
      user.addCommunity(community);

      User updated = userRepository.save(user);

      return toUserDto(updated);
   }

   private UserDTO toUserDto(User updated) {
      return new UserDTO(updated.getFirstName(), updated.getLastName(), updated.getUsername(), updated.getRoles(), updated.getCommunities().stream()
            .map(this::toCommunityDTO)
            .collect(toList()));
   }

   private CommunityDTO toCommunityDTO(Community community) {
      return new CommunityDTO(community.getId(), community.getName(), community.getCity().getId());
   }
}
