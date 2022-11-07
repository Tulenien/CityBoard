package com.CityBoard.interfaces;

import com.CityBoard.postgresql.dbmodels.UsersModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersRepository {
    Page<UsersModel> findAllUsers(Pageable pageable);
    UsersModel findUserById(Long id);
    UsersModel findUserByUsername(String username);
}
