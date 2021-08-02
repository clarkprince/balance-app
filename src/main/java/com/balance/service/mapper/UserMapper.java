package com.balance.service.mapper;

import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.dto.CommunityDTO;
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
      return new UserDTO(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getCity(), user.getAddress(), user.getPhoneNumber(), user.isActive(), user.getRoles(),
            user.getCommunities().stream().map(this::toCommunityDTO).collect(toList())
      );
   }

   private CommunityDTO toCommunityDTO(Community community) {
      return new CommunityDTO(community.getId(), community.getName(), community.getCity().getId());
   }

   public User mapUserFromSignupRequest(UserDTO userDTO) {
      User user = new User();
      user.setUsername(userDTO.getUsername());
      user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
      user.setFirstName(userDTO.getFirstName());
      user.setLastName(userDTO.getLastName());
      user.setCity(userDTO.getCity());
      user.setAddress(userDTO.getAddress());
      user.setPhoneNumber(userDTO.getPhoneNumber());
      user.setActive(false);
      user.setRoles(ROLE_USER);
      user.setCommunities(new ArrayList<>());
      return user;
   }

}
