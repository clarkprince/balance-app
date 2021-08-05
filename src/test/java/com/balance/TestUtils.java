package com.balance;

import com.balance.model.User;
import com.balance.model.VerificationToken;
import com.balance.model.dto.CommunityDTO;
import com.balance.model.dto.LoginRequest;
import com.balance.model.dto.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class TestUtils {


   public static final String TEST_FIRSTNAME = "John";
   public static final String TEST_LASTNAME = "Doe";
   public static final long TEST_USER_ID = 1L;
   public static final long TEST_TOKEN_ID = 2L;
   public static final String ROLE_USER = "ROLE_USER";

   public static final String TEST_USERNAME = "test@mail.com";
   public static final String TEST_PASSWORD = "HXbk5Zk6";
   private static final String TEST_CITY = "New York";
   private static final String TEST_PHONE_NUMBER = "555-6668-8589";
   private static final String TEST_ADDRESS = "1818 Broadway Street, New York";

   private TestUtils(){

   }

   public static VerificationToken prepareVerificationToken(User user) {
      VerificationToken verificationToken = new VerificationToken();
      verificationToken.setUser(user);
      verificationToken.setExpiration(new Date());
      verificationToken.setToken(UUID.randomUUID().toString());
      verificationToken.setId(TEST_TOKEN_ID);
      return verificationToken;
   }

   public static User prepareUser() {
      User user = new User();
      user.setActive(false);
      user.setCommunities(emptyList());
      user.setId(TEST_USER_ID);
      user.setFirstName(TEST_FIRSTNAME);
      user.setLastName(TEST_LASTNAME);
      user.setPassword(TEST_PASSWORD);
      user.setRoles(ROLE_USER);
      user.setUsername(TEST_USERNAME);
      user.setAddress(TEST_ADDRESS);
      user.setPhoneNumber(TEST_PHONE_NUMBER);
      user.setCity(TEST_CITY);
      return user;
   }
   public static User prepareUser(long id, String username) {
      User user = new User();
      user.setActive(false);
      user.setCommunities(emptyList());
      user.setId(id);
      user.setFirstName(TEST_FIRSTNAME);
      user.setLastName(TEST_LASTNAME);
      user.setPassword(TEST_PASSWORD);
      user.setRoles(ROLE_USER);
      user.setUsername(username);
      user.setAddress(TEST_ADDRESS);
      user.setPhoneNumber(TEST_PHONE_NUMBER);
      user.setCity(TEST_CITY);
      return user;
   }
   public static User prepareUserWithoutId(String username) {
      User user = new User();
      user.setActive(false);
      user.setCommunities(emptyList());
      user.setFirstName(TEST_FIRSTNAME);
      user.setLastName(TEST_LASTNAME);
      user.setPassword(TEST_PASSWORD);
      user.setRoles(ROLE_USER);
      user.setUsername(username);
      user.setAddress(TEST_ADDRESS);
      user.setPhoneNumber(TEST_PHONE_NUMBER);
      user.setCity(TEST_CITY);
      return user;
   }

   public static UserDTO prepareUserDto(User user) {
      List<CommunityDTO> communities = ofNullable(user.getCommunities()).orElse(emptyList()).stream()
            .map(community -> new CommunityDTO(community.getId(), community.getName(), community.getCity().getId()))
            .collect(toList());

      return new UserDTO(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getCity(),
            user.getAddress(), user.getPhoneNumber(), user.isActive(), user.getRoles(), communities);
   }

   public static CommunityDTO prepareCommunityDtoWithoutId(long cityId) {
      CommunityDTO communityDTO = new CommunityDTO();
      communityDTO.setCityId(cityId);
      communityDTO.setName(UUID.randomUUID().toString());
      return communityDTO;
   }

   public static UserDTO prepareSignupRequest() {
      UserDTO userDTO = new UserDTO();
      userDTO.setFirstName(TEST_FIRSTNAME);
      userDTO.setLastName(TEST_LASTNAME);
      userDTO.setUsername(TEST_USERNAME);
      userDTO.setPassword(TEST_PASSWORD);
      return userDTO;
   }


   public static LoginRequest prepareLoginRequest() {
      return new LoginRequest(TEST_USERNAME, TEST_PASSWORD);
   }


}
