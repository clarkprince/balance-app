package com.balance.service;

import com.balance.IntegrationTest;
import com.balance.config.JwtProvider;
import com.balance.model.User;
import com.balance.model.UserDetailsInfo;
import com.balance.model.VerificationToken;
import com.balance.model.dto.LoginRequest;
import com.balance.repository.UserRepository;
import com.balance.repository.VerificationTokenRepository;
import com.balance.service.user.AuthenticationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.UUID;

import static com.balance.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Sql({"classpath:/sql/users.sql", "classpath:/sql/verificationTokens.sql"})
public class AuthenticationServiceTest extends IntegrationTest {

   @Autowired
   private VerificationTokenRepository verificationTokenRepository;
   @Autowired
   private UserRepository userRepository;
   @MockBean
   private AuthenticationManager authenticationManager;
   @MockBean
   private JwtProvider jwtProvider;

   private AuthenticationService authenticationService;

   @BeforeAll
   public void init() {
      authenticationService = new AuthenticationService(userRepository, authenticationManager, jwtProvider, verificationTokenRepository);
   }


   @Test
   public void shouldVerifyTokenAndActivateUser() {
      //given
      String testMail = "test@mail.cc";
      User user = prepareUserWithoutId(testMail);
      VerificationToken verificationToken = prepareVerificationToken(user);
      verificationTokenRepository.save(verificationToken);

      assertFalse(user.isActive());
      //when
      User actualUser = authenticationService.verifyTokenAndActivateUser(verificationToken.getToken());

      //then
      assertEquals(user.getUsername(), actualUser.getUsername());
      assertTrue(actualUser.isActive());
   }

   @Test
   public void shouldActivateUser() {
      //before
      User user = userRepository.getById(2330L);

      assertFalse(user.isActive());

      //when
      User activatedUser = authenticationService.activateUser(user.getUsername(), true);

      //then
      assertEquals(user.getUsername(), activatedUser.getUsername());
      assertTrue(activatedUser.isActive());
   }

   @Test
   public void shouldDeactivateUser() {
      //before
      User user = userRepository.getById(2331L);
      assertTrue(user.isActive());

      //when
      User activatedUser = authenticationService.activateUser(user.getUsername(), false);

      //then
      assertEquals(user.getUsername(), activatedUser.getUsername());
      assertFalse(activatedUser.isActive());
   }

   @Test
   public void shouldLogin() {
      //given
      LoginRequest loginRequest = prepareLoginRequest();

      Authentication authentication = mock(Authentication.class);
      User user = prepareUser();
      UserDetailsInfo userDetailsInfo = new UserDetailsInfo(user);
      String token = UUID.randomUUID().toString();

      when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())))
            .thenReturn(authentication);
      when(authentication.getPrincipal()).thenReturn(userDetailsInfo);
      when(jwtProvider.generateToken(userDetailsInfo.getUsername())).thenReturn(token);

      //when
      String actualToken = authenticationService.login(loginRequest);

      //then
      verify(authenticationManager, times(1)).authenticate(any());
      verify(authentication, times(1)).getPrincipal();
      verify(jwtProvider, times(1)).generateToken(any());
      assertEquals(token, actualToken);
   }

   @Test
   public void shouldCreateVerificationToken() {
      //then
      String testMail = "test@mail.net";
      User user = userRepository.save(prepareUserWithoutId(testMail));
      //when
      VerificationToken verificationToken = authenticationService.createVerificationToken(user);

      //then
      VerificationToken tokenFromDb = verificationTokenRepository.getById(verificationToken.getId());
      assertEquals(verificationToken, tokenFromDb);
      assertNotNull(verificationToken.getToken());
      assertNotNull(verificationToken.getStartDate());
      assertNotNull(verificationToken.getEndDate());
      assertEquals(user.getUsername(), verificationToken.getUser().getUsername());
   }

   @Test
   public void shouldGetTokenByUsername() {
      //then
      String testMail = "test@mail.net";
      User user = userRepository.save(prepareUserWithoutId(testMail));
      VerificationToken newToken = verificationTokenRepository.save(prepareVerificationToken(user));
      //when
      VerificationToken actualToken = authenticationService.getTokenByUsername(user.getUsername());

      //then
      assertEquals(actualToken, newToken);
      assertNotNull(actualToken.getToken());
      assertNotNull(actualToken.getStartDate());
      assertNotNull(actualToken.getEndDate());
      assertEquals(user.getUsername(), actualToken.getUser().getUsername());
   }

   private VerificationToken createNewVerificationTokenForUser(User user) {
      VerificationToken verificationToken = new VerificationToken();
      verificationToken.setUser(user);
      verificationToken.setExpiration(new Date());
      return verificationTokenRepository.save(verificationToken);
   }
}
