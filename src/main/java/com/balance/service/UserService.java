package com.balance.service;

import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.UserDetailsInfo;
import com.balance.repository.UserRepository;
import com.balance.config.JwtProvider;
import com.balance.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserService {

   private static final String ROLE_USER = "ROLE_USER";
   private final UserRepository userRepository;
   private final AuthenticationManager authenticationManager;
   private final PasswordEncoder passwordEncoder;
   private final JwtProvider jwtProvider;

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

      return UserDTO.builder()
            .username(updated.getUsername())
            .firstName(updated.getFirstName())
            .lastName(updated.getLastName())
            .roles(updated.getRoles())
            .communities(updated.getCommunities().stream()
                  .map(c -> CommunityDTO.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .cityId(c.getCity().getId())
                        .build())
                  .collect(toList()))
            .build();
   }
}
