package com.carrentalproject.security.service;

import com.carrentalproject.domain.User;
import com.carrentalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username"+email));
      return UserDetailsImpl.build(user); //build metodu ile user bilgileri gelicek o bilgileri dondurecek

    }
}
