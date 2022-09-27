package com.CityBoard.services;

import com.CityBoard.models.Users;
import com.CityBoard.repositories.UsersRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {

    private UsersService usersService;
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        usersRepository = Mockito.mock(UsersRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        usersService = new UsersService(usersRepository, passwordEncoder);
        customUserDetailsService = new CustomUserDetailsService(usersService);
    }

    @Test
    void userNotFound_throwsUsernameNotFoundException() {
        String notExistingUser = "Jack";

        Mockito.when(usersRepository.findByUsername(notExistingUser)).thenReturn(null);
        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(notExistingUser);
        }, "UsernameNotFoundException was expected");

        Assertions.assertEquals("Username not found", thrown.getMessage());
    }

    @Test
    void userExists_returnsThisUser() {
        String username = "Jack";
        String password = "p@s2w0rD";
        Users user = new Users();
        user.setName(username);
        user.setPassword(password);

        Mockito.when(usersRepository.findByUsername(username)).thenReturn(user);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }


}