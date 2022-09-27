package com.CityBoard.services;

import com.CityBoard.configuration.SecurityConfiguration;
import com.CityBoard.dto.UserCredentialsDTO;
import com.CityBoard.dto.UserEncodedDTO;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersService usersService;
    private final SecurityConfiguration securityConfiguration;

    public CustomUserDetailsService(UsersService usersService,
                                    SecurityConfiguration securityConfiguration) {
        this.usersService = usersService;
        this.securityConfiguration = securityConfiguration;
        securityConfiguration.setUserDetailsService(this);
    }

    public boolean userExists(String username) {
        return usersService.getUserByUsername(username) != null;
    }

    public void registerUser(UserCredentialsDTO userCredentials) throws Exception {
        if (userExists(userCredentials.getUsername()))
        {
            throw new Exception("User with this username already exists");
        }
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        UserEncodedDTO userEncoded = UserEncodedDTO.builder()
                .userStatus(UserStatus.ACTIVE)
                .username(userCredentials.getUsername())
                .encodedPassword(securityConfiguration.passwordEncoder().encode(userCredentials.getPassword()))
                .roles(userRoles)
                .password_expired(false)
                .build();
        usersService.createUserFromEncodedDTO(userEncoded);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersService.getUserByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
