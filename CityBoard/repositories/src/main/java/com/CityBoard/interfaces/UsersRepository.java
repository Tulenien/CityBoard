package com.CityBoard.interfaces;

import com.CityBoard.tto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersRepository {
    Page<UserDTO> findAllUsers(Pageable pageable);

    UserDTO findUserById(Long id);

    UserDTO findUserByUsername(String username);

    void persist(UserDTO model);

    void update(UserDTO model);
}
