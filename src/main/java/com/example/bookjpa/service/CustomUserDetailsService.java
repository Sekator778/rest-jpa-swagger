package com.example.bookjpa.service;

import com.example.bookjpa.domain.AppUser;
import com.example.bookjpa.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername init");
        Optional<AppUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            AppUser appuser = user.get();
            log.info("user for registration found {}", appuser);
            return User.withUsername(appuser.getEmail()).password(appuser.getPassword()).authorities("USER").build();
        } else {
            throw new UsernameNotFoundException(String.format("Email[%s] not found", username));
        }
    }
}