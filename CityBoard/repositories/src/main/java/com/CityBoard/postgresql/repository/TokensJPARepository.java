package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.CommonRepository;
import com.CityBoard.postgresql.dto.JwtToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokensJPARepository extends CommonRepository<JwtToken> {
    JwtToken findByUsername(String username);

    Optional<JwtToken> findById(Long id);
}
