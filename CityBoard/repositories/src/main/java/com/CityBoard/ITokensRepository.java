package com.CityBoard;

import com.CityBoard.postgresql.dto.JwtToken;

import java.util.Optional;

public interface ITokensRepository {
    JwtToken findByUsername(String username);
    Optional<JwtToken> findById(Long id);
}
