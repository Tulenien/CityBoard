package com.CityBoard.services;

import com.CityBoard.models.Users;
import com.CityBoard.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        logger.debug("User authentication: {}, {}", user.getUsername(), user.getPassword());
        throw new UsernameNotFoundException("Username not found");
    }
}
