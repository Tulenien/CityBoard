package com.CityBoard.services;


import com.CityBoard.models.Users;
import com.CityBoard.models.dto.UserCredentialsDTO;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService extends AbstractService<Users, UsersRepository> {
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

    public List<Users> getUsersList() {
        return repository.findAll();
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
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            save(user);
        }
    }

    public void removeRole(Users user, Roles role) {
        if (user != null && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            save(user);
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
