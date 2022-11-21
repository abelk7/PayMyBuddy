package com.paymybuddy.paymybuddybackend.service.impl;

import com.paymybuddy.paymybuddybackend.exception.UserNotFoundException;
import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.repository.UserRepository;
import com.paymybuddy.paymybuddybackend.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service("userService")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            log.error("User with email : {} are not Found", email);
            throw new UserNotFoundException("Votre identifiant est incorrect");


        } else {
            log.error("User with email : {} Found ", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} {} to the database", user.getNom(), user.getPrenom());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
