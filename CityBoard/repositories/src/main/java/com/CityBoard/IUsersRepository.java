package com.CityBoard;

import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUsersRepository {
    Page<Users> findAll(Pageable pageable);
    Users findByUsername(String username);

    Users findById(Long id);
}
