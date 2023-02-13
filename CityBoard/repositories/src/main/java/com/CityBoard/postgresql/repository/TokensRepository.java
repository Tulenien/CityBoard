package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.CommonRepository;
import com.CityBoard.postgresql.dto.JwtToken;
import com.CityBoard.postgresql.dto.UserDTO;

import java.util.Optional;

public interface TokensRepository extends CommonRepository<JwtToken> {
    JwtToken findByUsername(String username);
    Optional<JwtToken> findById(Long id);
}
