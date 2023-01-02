package com.CityBoard.common.repository;

import com.CityBoard.dto.UserDTO;
import com.CityBoard.postgresql.dbmodels.UsersModelImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersRepository {
    Page<UserDTO> findAllUsers(Pageable pageable);

    UserDTO findUserById(Long id);

    UserDTO findUserByUsername(String username);

    void persist(UserDTO model);

    void update(UserDTO model);
}
