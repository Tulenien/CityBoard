package com.CityBoard.services;

//import com.CityBoard.dto.UserProfileDTO;

import com.CityBoard.dto.UserCredentialsDTO;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.models.enums.UserStatus;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService extends AbstractService<Users, UsersRepository> {
    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    public Users getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean userExists(String username) {
        return getUserByUsername(username) != null;
    }

    public void registerUser(UserCredentialsDTO userCredentials) throws Exception {
        if (userExists(userCredentials.getUsername())) {
            throw new Exception("User with this username already exists");
        }
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        Users user = Users.builder()
                .status(UserStatus.ACTIVE)
                .username(userCredentials.getUsername())
                .password(passwordEncoder.encode(userCredentials.getPassword()))
                .roles(userRoles)
                .password_expired(false)
                .build();
        save(user);
    }

    public void addRole(Users user, Roles role) {
        Set<Roles> roles = user.getRoles();
        if (!roles.contains(role)) {
            roles.add(role);
            user.setRoles(roles);
        }
    }

    public void removeRole(Users user, Roles role) {
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
