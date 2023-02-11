package com.CityBoard.services;


import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dbmodels.UsersPostgres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService extends AbstractService<UsersPostgres, UsersRepository> {
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Users> getAllUsersPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UsersPostgres> dtoPage = repository.findAll(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Users getUserByUsername(String username) {
        return repository.findByUsername(username).mapDTOtoEntity();
    }

    public Users getUserById(Long userId) {
        UsersPostgres user = repository.findById(userId).orElse(null);
        if (user != null) {
            return user.mapDTOtoEntity();
        }
        return null;
    }

    public UsersPostgres getUserDTOByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UsersPostgres getUserDTOById(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    public boolean usernameExists(String username) {
        return repository.findByUsername(username) != null;
    }

    private String cryptPassword(String password) {
        if (passwordEncoder == null) {
            return "{noop}" + password;
        }
        return passwordEncoder.encode(password);
    }

    public UsersPostgres createUser(Users user) throws Exception {
        if (usernameExists(user.getUsername())) {
            throw new Exception("User with this username already exists");
        }
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        UsersPostgres userDTO = new UsersPostgres();
        user.setPassword(cryptPassword(user.getPassword()));
        userDTO.mapEntity(user);
        userDTO.setRoles(userRoles);
        return userDTO;
    }

    public boolean addRole(Long userId, Roles role) {
        UsersPostgres user = repository.findById(userId).orElse(null);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            save(user);
            return true;
        }
        return false;
    }

    public boolean removeRole(Long userId, Roles role) {
        UsersPostgres user = repository.findById(userId).orElse(null);
        if (user != null && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            save(user);
            return true;
        }
        return false;
    }

    private Page<Users> mapDTOtoEntityPage(Page<UsersPostgres> dtoPage, Pageable pageable) {
        List<UsersPostgres> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Users> usersList = new ArrayList<>();
        for (UsersPostgres dto : dtoList) {
            usersList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(usersList, pageable, totalElements);
    }

    @Override
    public void save(UsersPostgres entity) {
        repository.save(entity);
    }

    @Override
    public void delete(UsersPostgres entity) {
        repository.delete(entity);
    }
}
