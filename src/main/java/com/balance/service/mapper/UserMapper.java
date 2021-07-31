package com.balance.service.mapper;

import com.balance.model.Community;
import com.balance.model.User;
import com.balance.model.dto.CommunityDTO;
import com.balance.model.dto.SignupRequest;
import com.balance.model.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class UserMapper {
   private final PasswordEncoder passwordEncoder;
   private static final String ROLE_USER = "ROLE_USER";


   public UserMapper(PasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
   }


   public UserDTO toUserDto(User user) {
      return new UserDTO(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.isActive(), user.getRoles(),
            user.getCommunities().stream().map(this::toCommunityDTO).collect(toList())
      );
   }

   private CommunityDTO toCommunityDTO(Community community) {
      return new CommunityDTO(community.getId(), community.getName(), community.getCity().getId());
   }

   public User mapUserFromSignupRequest(SignupRequest signupRequest) {
      User user = new User();
      user.setUsername(signupRequest.getUsername());
      user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
      user.setFirstName(signupRequest.getFirstName());
      user.setLastName(signupRequest.getLastName());
      user.setEmail(signupRequest.getEmail());
      user.setActive(false);
      String roles = signupRequest.getRoles();
      if (isEmpty(roles)) {
         roles = ROLE_USER;
      }
      user.setRoles(roles);
      return user;
   }

}
