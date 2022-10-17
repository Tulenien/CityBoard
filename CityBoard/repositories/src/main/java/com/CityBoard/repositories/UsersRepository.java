package com.CityBoard.repositories;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CommonRepository<Users> {
    List<Users> findAll();

    Users findByUsername(String username);

    Optional<Users> findById(Long id);

    List<Users> findByRoles(Roles role);
}
