package com.CityBoard.services;

import com.CityBoard.DTO.UserCredentialsDTO;
import com.CityBoard.models.Roles;
import com.CityBoard.models.UserStatus;
import com.CityBoard.models.Users;
import com.CityBoard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null ? true : false;
    }

    public void registerUser(UserCredentialsDTO userCredentials) throws Exception {
        if (userExists(userCredentials.getUsername()))
        {
            throw new Exception("User with this username already exists");
        }
        System.out.println("We are here!\n");
        Set<Roles> userRoles = new HashSet<Roles>();
        userRoles.add(Roles.ROLE_USER);
        Users user = Users.builder()
                .username(userCredentials.getUsername())
                .password(passwordEncoder.encode(userCredentials.getPassword()))
                .created_at(new Timestamp(System.currentTimeMillis()))
                .password_expired(false)
                .status(UserStatus.LOGGED_OFF)
                .roles(userRoles)
                .build();
        userRepository.save(user);
    }
}
