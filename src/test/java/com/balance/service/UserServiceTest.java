package com.balance.service;

import com.balance.IntegrationTest;
import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.VerificationToken;
import com.balance.model.dto.LoginRequest;
import com.balance.model.dto.LoginResponse;
import com.balance.model.dto.UserDTO;
import com.balance.repository.CommunityRepository;
import com.balance.repository.UserRepository;
import com.balance.service.mapper.UserMapper;
import com.balance.service.user.AuthenticationService;
import com.balance.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.balance.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest extends IntegrationTest {
   private UserService userService;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private CommunityRepository communityRepository;
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
   public void shouldSignupAndSendVerificationEmail() {
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
   public void shouldLoginAndReturnUser() {
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
      //given
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

   @Test
   @Sql(value = {"classpath:/sql/cities.sql", "classpath:/sql/communities.sql", "classpath:/sql/users.sql"}, config = @SqlConfig(separator = ";"))
   public void shouldAttachCommunityToUser() {
      //given
      Community community = communityRepository.getById(100300L);
      //when
      userService.attachCommunityToUser(2329L, community);
      //then
      User user = userRepository.getById(2329L);
      assertEquals("john.doe@email.com", user.getUsername());
      assertEquals(community.getId(), user.getCommunities().get(0).getId());
   }

   @Test
   public void resendAuthToken() {
      //given
      User user = prepareUser();
      VerificationToken verificationToken = prepareVerificationToken(user);
      when(authenticationService.getTokenByUsername(TEST_USERNAME)).thenReturn(verificationToken);

      //when
      userService.resendAuthToken(TEST_USERNAME);

      //then
      verify(authenticationService, times(1)).getTokenByUsername(TEST_USERNAME);
      verify(emailService, times(1)).sendVerificationTokenEmail(verificationToken);
   }

}
