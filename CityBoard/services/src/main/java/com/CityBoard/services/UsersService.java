package com.CityBoard.services;


import com.CityBoard.models.Users;
import com.CityBoard.models.dto.UserCredentialsDTO;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import com.CityBoard.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService extends AbstractService<Users, UsersRepository> {
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    public Users getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Users getUserById(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    public boolean userExists(String username) {
        return repository.findByUsername(username) != null;
    }

    private String cryptPassword(String password) {
        if (passwordEncoder == null) {
            return "{noop}" + password;
        }
        return passwordEncoder.encode(password);
    }

    public Users registerUser(UserCredentialsDTO userCredentials) throws Exception {
        if (userExists(userCredentials.getUsername())) {
            logger.warn("Use of existing username", userCredentials.getUsername());
            throw new Exception("User with this username already exists");
        }
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        Users user = Users.builder()
                .status(UserStatus.ACTIVE)
                .username(userCredentials.getUsername())
                .password(cryptPassword(userCredentials.getPassword()))
                .roles(userRoles)
                .password_expired(false)
                .build();
        repository.save(user);
        return user;
    }

    public void addRole(Users user, Roles role) {
        logger.warn("Add role {} to user {}", role, user.getId());
        Set<Roles> roles = user.getRoles();
        if (!roles.contains(role)) {
            roles.add(role);
            user.setRoles(roles);
        }
    }

    public void removeRole(Users user, Roles role) {
        logger.warn("remove role {} from user {}", role, user.getId());
        Set<Roles> roles = user.getRoles();
        if (roles.contains(role)) {
            roles.remove(role);
            user.setRoles(roles);
        }
    }

    @Override
    public void save(Users entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Users entity) {
        repository.delete(entity);
    }
}
