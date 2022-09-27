package com.CityBoard.services;

import com.CityBoard.dto.UserCredentialsDTO;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import com.CityBoard.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {
    private UsersService usersService;
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        usersRepository = Mockito.mock(UsersRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        usersService = new UsersService(usersRepository, passwordEncoder);
    }

    @Test
    void getUserByCorrectUsername_shouldReturnUsers() {
        String username = "1";
        Users user = new Users();
        user.setUsername("1");
        user.setStatus(UserStatus.ACTIVE);
        Mockito.when(usersRepository.findByUsername(username)).thenReturn(user);
        assertEquals(usersService.getUserByUsername(username), user);
        assertEquals(usersService.userExists(username), true);
    }

    @Test
    void getUserByIncorrectUsername_shouldReturnNull() {
        String correct = "1";
        String incorrect = "2";
        Users user = new Users();
        user.setUsername("1");
        user.setStatus(UserStatus.ACTIVE);
        Mockito.when(usersRepository.findByUsername(correct)).thenReturn(user);
        Mockito.when(usersRepository.findByUsername(incorrect)).thenReturn(null);
        assertNull(usersService.getUserByUsername(incorrect));
        assertEquals(usersService.userExists(incorrect), false);
    }

    private boolean partlyEquals(Users user1, Users user2) {
        return user1.getPassword().equals(user2.getPassword()) &&
                user1.getUsername().equals(user2.getUsername()) &&
                user1.getRoles().equals(user2.getRoles()) &&
                user1.getStatus().equals(user2.getStatus());
    }

    @Test
    void registerUser_shouldCryptPassword() {
        String username = "username";
        String crypted = "wordpass";
        String password = "password";
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        Users expected = Users.builder()
                .status(UserStatus.ACTIVE)
                .username(username)
                .password(crypted)
                .roles(userRoles)
                .build();
        UserCredentialsDTO credentials = UserCredentialsDTO.builder()
                .username(username)
                .password(password)
                .build();
        Mockito.when(passwordEncoder.encode(password)).thenReturn(crypted);
        Mockito.doAnswer(invocationOnMock -> {
            Users arg0 = invocationOnMock.getArgument(0);

            assertTrue(partlyEquals(arg0, expected));
            return null;
        }).when(usersRepository).save(Mockito.any(Users.class));
        assertDoesNotThrow(() -> usersService.registerUser(credentials));
    }

    @Test
    void registerUserWhenThisUserExists_shouldThrowException() {
        String username = "username";
        UserCredentialsDTO credentials = UserCredentialsDTO.builder()
                .username(username)
                .build();
        Mockito.when(usersRepository.findByUsername(username)).thenReturn(new Users());
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            usersService.registerUser(credentials);
        }, "Exception was expected");
        Assertions.assertEquals("User with this username already exists", thrown.getMessage());
    }

    @Test
    void addExistingRole_shouldNotInsert() {
        Set<Roles> expected = new HashSet<>();
        Set<Roles> actual = new HashSet<>();
        expected.add(Roles.ROLE_USER);
        actual.add(Roles.ROLE_USER);
        Users user = new Users();
        user.setRoles(actual);

        usersService.addRole(user, Roles.ROLE_USER);

        assertEquals(user.getRoles(), expected);
    }

    @Test
    void addNotExistingRole_shouldAdd() {
        Set<Roles> expected = new HashSet<>();
        Set<Roles> actual = new HashSet<>();
        expected.add(Roles.ROLE_USER);
        Users user = new Users();
        user.setRoles(actual);

        usersService.addRole(user, Roles.ROLE_USER);

        assertEquals(user.getRoles(), expected);
    }

    @Test
    void removeNotExistingRole_shouldNotRemove() {
        Set<Roles> expected = new HashSet<>();
        Set<Roles> actual = new HashSet<>();
        expected.add(Roles.ROLE_ADMIN);
        actual.add(Roles.ROLE_ADMIN);
        Users user = new Users();
        user.setRoles(actual);

        usersService.removeRole(user, Roles.ROLE_USER);

        assertEquals(user.getRoles(), expected);
    }

    @Test
    void removeExistingRole_shouldBeRemoved() {
        Set<Roles> expected = new HashSet<>();
        Set<Roles> actual = new HashSet<>();
        expected.add(Roles.ROLE_ADMIN);
        actual.add(Roles.ROLE_ADMIN);
        actual.add(Roles.ROLE_USER);
        Users user = new Users();
        user.setRoles(actual);

        usersService.removeRole(user, Roles.ROLE_USER);

        assertEquals(user.getRoles(), expected);
    }
}