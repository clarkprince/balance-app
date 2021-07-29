package com.balance.service;

import com.balance.repository.UserRepository;
import com.balance.model.User;
import com.balance.model.UserDetailsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailsInfoService implements UserDetailsService {

   private final UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

      return new UserDetailsInfo(user);
   }
}
