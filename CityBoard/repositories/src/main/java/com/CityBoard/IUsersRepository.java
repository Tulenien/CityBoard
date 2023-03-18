package com.CityBoard;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsersRepository {
    Page<Users> findAll(Pageable pageable);

    Users findByUsername(String username);

    Users findById(Long id);

    void saveUser(Users user);

    boolean addRole(Long userId, Roles role);

    boolean removeRole(Long userId, Roles role);
}
