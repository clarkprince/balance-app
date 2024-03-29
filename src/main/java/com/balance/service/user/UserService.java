package com.balance.service.user;

import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.VerificationToken;
import com.balance.model.dto.LoginRequest;
import com.balance.model.dto.LoginResponse;
import com.balance.model.dto.UserDTO;
import com.balance.repository.UserRepository;
import com.balance.service.EmailService;
import com.balance.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserService {
   private static final Logger log = LoggerFactory.getLogger(UserService.class);

   private final UserRepository userRepository;
   private final UserMapper userMapper;
   private final AuthenticationService authenticationService;
   private final EmailService emailService;

   public UserService(UserRepository userRepository,
                      UserMapper userMapper,
                      AuthenticationService authenticationService,
                      EmailService emailService) {
      this.userMapper = userMapper;
      this.userRepository = userRepository;
      this.authenticationService = authenticationService;
      this.emailService = emailService;
   }

   @Transactional
   public VerificationToken signupAndSendVerificationEmail(UserDTO userDTO) {
      log.info("Signing up user {}", userDTO.getUsername());
      User user = userMapper.mapUserFromSignupRequest(userDTO);
      User newUser = authenticationService.signup(user);
      VerificationToken verificationToken = authenticationService.createVerificationToken(newUser);
      emailService.sendVerificationTokenEmail(verificationToken);
      return verificationToken;
   }

   public LoginResponse login(LoginRequest loginRequest) {
      String token = authenticationService.login(loginRequest);
      return new LoginResponse(token);
   }

   public UserDTO verifyTokenAndActivateUser(String token) {
      User user = authenticationService.verifyTokenAndActivateUser(token);
      return userMapper.toUserDto(user);
   }

   public void resendAuthToken(String username) {
      VerificationToken token = authenticationService.getTokenByUsername(username);
      emailService.sendVerificationTokenEmail(token);
   }

   @Transactional
//   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public UserDTO attachCommunityToUser(Long userId, Community community) {
      User user = userRepository.getById(userId);
      user.addCommunity(community);
      return userMapper.toUserDto(user);
   }
}
