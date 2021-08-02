package com.balance.service.mapper;

import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.dto.CommunityDTO;
import com.balance.model.dto.SignupRequest;
import com.balance.model.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

@Component
public class UserMapper {
   private static final String ROLE_USER = "ROLE_USER";

   private final PasswordEncoder passwordEncoder;

   public UserMapper(PasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
   }


   public UserDTO toUserDto(User user) {
      return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.isActive(), user.getRoles(),
            user.getCommunities().stream().map(this::toCommunityDTO).collect(toList())
      );
   }

   private CommunityDTO toCommunityDTO(Community community) {
      return new CommunityDTO(community.getId(), community.getName(), community.getCity().getId());
   }

   public User mapUserFromSignupRequest(SignupRequest signupRequest) {
      User user = new User();
      user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
      user.setFirstName(signupRequest.getFirstName());
      user.setLastName(signupRequest.getLastName());
      user.setEmail(signupRequest.getEmail());
      user.setActive(false);
      user.setRoles(ROLE_USER);
      user.setCommunities(new ArrayList<>());
      return user;
   }

}
