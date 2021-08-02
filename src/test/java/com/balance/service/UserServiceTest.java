package com.balance.service;

import com.balance.IntegrationTest;
import com.balance.model.User;
import com.balance.model.VerificationToken;
import com.balance.model.dto.SignupRequest;
import com.balance.repository.UserRepository;
import com.balance.service.mapper.UserMapper;
import com.balance.service.user.AuthenticationService;
import com.balance.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest extends IntegrationTest {

   private static final String TEST_FIRSTNAME = "John";
   private static final String TEST_LASTNAME = "Doe";
   private static final String TEST_USERNAME = "test@mail.com";
   private static final String TEST_PASSWORD = "HXbk5Zk6";
   private static final long TEST_USER_ID = 1L;
   private static final long TEST_TOKEN_ID = 2L;
   private static final String ROLE_USER = "ROLE_USER";
   private UserService userService;
   @MockBean
   private UserRepository userRepository;
   @MockBean
   private UserMapper userMapper;
   @MockBean
   private AuthenticationService authenticationService;
   @MockBean
   private EmailService emailService;

   @BeforeAll
   public void init() {
      userService = new UserService(userRepository, userMapper, authenticationService, emailService);
   }

   @Test
   public void shouldSignupAndSendVerificationEmail() throws Exception {
      //given
      SignupRequest signupRequest = prepareSignupRequest();
      User user = prepareUser();
      VerificationToken verificationToken = prepareVerificationToken(user);

      when(userMapper.mapUserFromSignupRequest(signupRequest)).thenReturn(user);
      when(authenticationService.signup(user)).thenReturn(user);
      when(authenticationService.createVerificationToken(user)).thenReturn(verificationToken);

      //when
      VerificationToken actualToken = userService.signupAndSendVerificationEmail(signupRequest);

      //then
      assertEquals(verificationToken, actualToken);
      verify(userMapper, times(1)).mapUserFromSignupRequest(eq(signupRequest));
      verify(authenticationService, times(1)).signup(eq(user));
      verify(authenticationService, times(1)).createVerificationToken(eq(user));
      verify(emailService, times(1)).sendVerificationTokenEmail(eq(actualToken));
   }

   private VerificationToken prepareVerificationToken(User user) {
      VerificationToken verificationToken = new VerificationToken();
      verificationToken.setUser(user);
      verificationToken.setExpiration(new Date());
      verificationToken.setToken(UUID.randomUUID().toString());
      verificationToken.setId(TEST_TOKEN_ID);
      return verificationToken;
   }

   private User prepareUser() {
      User user = new User();
      user.setActive(false);
      user.setCommunities(Collections.emptyList());
      user.setId(TEST_USER_ID);
      user.setFirstName(TEST_FIRSTNAME);
      user.setLastName(TEST_LASTNAME);
      user.setPassword(TEST_PASSWORD);
      user.setRoles(ROLE_USER);
      return user;
   }

   private SignupRequest prepareSignupRequest() {
      SignupRequest signupRequest = new SignupRequest();
      signupRequest.setFirstName(TEST_FIRSTNAME);
      signupRequest.setLastName(TEST_LASTNAME);
      signupRequest.setUsername(TEST_USERNAME);
      signupRequest.setPassword(TEST_PASSWORD);
      return signupRequest;
   }

}
