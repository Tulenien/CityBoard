package com.CityBoard;

import com.CityBoard.postgresql.dto.JwtToken;

import java.util.Optional;

public class TokensRepository implements ITokensRepository {

    @Override
    public JwtToken findByUsername(String username) {
        return null;
    }

    @Override
    public Optional<JwtToken> findById(Long id) {
        return Optional.empty();
    }
}
