package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.CommonRepository;
import com.CityBoard.postgresql.dto.JwtToken;

import java.util.Optional;

public interface TokensJPARepository extends CommonRepository<JwtToken> {
    JwtToken findByUsername(String username);
    Optional<JwtToken> findById(Long id);
}
