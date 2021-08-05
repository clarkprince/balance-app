package com.balance.service;

import com.balance.IntegrationTest;
import com.balance.repository.UserRepository;
import com.balance.service.user.UserDetailsInfoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsInfoServiceTest extends IntegrationTest {


   @Autowired
   private UserRepository userRepository;
   private UserDetailsInfoService userDetailsInfoService;

   @BeforeAll
   public void init() {
      this.userDetailsInfoService = new UserDetailsInfoService(userRepository);
   }

   @Test
   @Sql("classpath:/sql/users.sql")
   public void shouldLoadUserByUsername() {
      //given
      String username = "josh.doe@email.com";

      //when
      UserDetails userDetails = userDetailsInfoService.loadUserByUsername(username);

      //then
      assertNotNull(userDetails);
      assertEquals(username, userDetails.getUsername());
   }

   @Test
   public void shouldThrowUserNotFoundExceptionWhenLoadUserByUsername() {
      //given
      String username = UUID.randomUUID().toString();

      //then-when
      assertThrows(UsernameNotFoundException.class, () -> userDetailsInfoService.loadUserByUsername(username), "Not found: " + username);
   }


}
