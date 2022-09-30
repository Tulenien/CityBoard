package com.CityBoard.repositories;

import com.CityBoard.models.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CommonRepository<Users> {
    Users findByUsername(String username);
}
