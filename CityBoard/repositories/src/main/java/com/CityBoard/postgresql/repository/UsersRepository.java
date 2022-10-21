package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.CommonRepository;
import com.CityBoard.postgresql.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CommonRepository<UserDTO> {
    Page<UserDTO> findAll(Pageable pageable);

    UserDTO findByUsername(String username);

    Optional<UserDTO> findById(Long id);
}
