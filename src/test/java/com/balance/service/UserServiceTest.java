package com.balance.service;

import com.balance.IntegrationTest;
import com.balance.model.User;
import com.balance.model.VerificationToken;
import com.balance.model.dto.LoginRequest;
import com.balance.model.dto.LoginResponse;
import com.balance.model.dto.UserDTO;
import com.balance.repository.UserRepository;
import com.balance.service.mapper.UserMapper;
import com.balance.service.user.AuthenticationService;
import com.balance.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.UUID;

import static java.util.Collections.emptyList;
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
      UserDTO signupRequest = prepareSignupRequest();
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

   @Test
   public void shouldLoginAndReturnUser() throws Exception {
      //given
      LoginRequest loginRequest = new LoginRequest();
      loginRequest.setUsername(TEST_USERNAME);
      loginRequest.setPassword(TEST_PASSWORD);
      String token = UUID.randomUUID().toString();
      when(authenticationService.login(loginRequest)).thenReturn(token);

      //when
      LoginResponse loginResponse = userService.login(loginRequest);

      //then
      verify(authenticationService, times(1)).login(eq(loginRequest));
      assertEquals(token, loginResponse.getToken());
   }

   @Test
   public void shouldVerifyTokenAndActivateUser() {
      String token = UUID.randomUUID().toString();
      User user = prepareUser();
      when(authenticationService.verifyTokenAndActivateUser(token)).thenReturn(user);
      when(userMapper.toUserDto(user)).thenReturn(new UserDTO());

      //when
      userService.verifyTokenAndActivateUser(token);

      //then
      verify(authenticationService, times(1)).verifyTokenAndActivateUser(eq(token));
      verify(userMapper, times(1)).toUserDto(eq(user));
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
      user.setCommunities(emptyList());
      user.setId(TEST_USER_ID);
      user.setFirstName(TEST_FIRSTNAME);
      user.setLastName(TEST_LASTNAME);
      user.setPassword(TEST_PASSWORD);
      user.setRoles(ROLE_USER);
      return user;
   }

   private UserDTO prepareSignupRequest() {
      UserDTO userDTO = new UserDTO();
      userDTO.setFirstName(TEST_FIRSTNAME);
      userDTO.setLastName(TEST_LASTNAME);
      userDTO.setUsername(TEST_USERNAME);
      userDTO.setPassword(TEST_PASSWORD);
      return userDTO;
   }

}
