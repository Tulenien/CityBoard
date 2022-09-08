package com.CityBoard.services;

import com.CityBoard.configuration.SecurityConfiguration;
import com.CityBoard.models.enums.UserStatus;
import com.CityBoard.models.Users;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository userRepository;
    private final SecurityConfiguration securityConfiguration;

    public CustomUserDetailsService(UsersRepository userRepository,
                                    SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
        securityConfiguration.setUserDetailsService(this);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
            return user;
        }
        throw new UsernameNotFoundException("Username not found");
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    //public void registerUser(UserCredentialsDTO userCredentials) throws Exception {
    //    if (userExists(userCredentials.getUsername()))
    //    {
    //        throw new Exception("User with this username already exists");
    //    }
    //    Set<Roles> userRoles = new HashSet<Roles>();
    //    userRoles.add(Roles.ROLE_USER);
    //    Users user = Users.builder()
    //            .username(userCredentials.getUsername())
    //            .password(securityConfiguration.passwordEncoder().encode(userCredentials.getPassword()))
    //            .password_expired(false)
    //            .status(UserStatus.LOGGED_OFF)
    //            .roles(userRoles)
    //            .build();
    //    userRepository.save(user);
    //}
}
