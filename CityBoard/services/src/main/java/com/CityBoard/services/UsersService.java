package com.CityBoard.services;


import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dbmodels.UsersModel;
import com.CityBoard.postgresql.repository.UsersRepository;
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
public class UsersService extends AbstractService<UsersModel, UsersRepository> {
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Users> getAllUsersPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<UsersModel> dtoPage = repository.findAll(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Users getUserByUsername(String username) {
        return repository.findByUsername(username).mapDTOtoEntity();
    }

    public Users getUserById(Long userId) {
        UsersModel user = repository.findById(userId).orElse(null);
        if (user != null) {
            return user.mapDTOtoEntity();
        }
        return null;
    }

    public UsersModel getUserDTOByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UsersModel getUserDTOById(Long userId) {
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

    public UsersModel createUser(Users user) throws Exception {
        if (usernameExists(user.getUsername())) {
            throw new Exception("User with this username already exists");
        }
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.ROLE_USER);
        UsersModel userDTO = new UsersModel();
        user.setPassword(cryptPassword(user.getPassword()));
        userDTO.mapEntity(user);
        userDTO.setRoles(userRoles);
        return userDTO;
    }

    public boolean addRole(Long userId, Roles role) {
        UsersModel user = repository.findById(userId).orElse(null);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            save(user);
            return true;
        }
        return false;
    }

    public boolean removeRole(Long userId, Roles role) {
        UsersModel user = repository.findById(userId).orElse(null);
        if (user != null && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            save(user);
            return true;
        }
        return false;
    }

    private Page<Users> mapDTOtoEntityPage(Page<UsersModel> dtoPage, Pageable pageable) {
        List<UsersModel> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Users> usersList = new ArrayList<>();
        for (UsersModel dto : dtoList) {
            usersList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(usersList, pageable, totalElements);
    }

    @Override
    public void save(UsersModel entity) {
        repository.save(entity);
    }

    @Override
    public void delete(UsersModel entity) {
        repository.delete(entity);
    }
}
