package com.CityBoard.services;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomUserDetailsServiceTest {
    private UsersRepository usersRepository;
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        usersRepository = Mockito.mock(UsersRepository.class);
        customUserDetailsService = new CustomUserDetailsService(usersRepository);
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
        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ROLE_USER);
        Users user = new Users();
        user.setName(username);
        user.setPassword(password);
        user.setRoles(roles);

        Mockito.when(usersRepository.findByUsername(username)).thenReturn(user);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), user.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getAuthorities(), userDetails.getAuthorities());
    }
}