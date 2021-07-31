package com.balance.service.user;

import com.balance.model.User;
import com.balance.model.UserDetailsInfo;
import com.balance.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class UserDetailsInfoService implements UserDetailsService {

   private final UserRepository userRepository;

   public UserDetailsInfoService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

      return new UserDetailsInfo(user);
   }
}
