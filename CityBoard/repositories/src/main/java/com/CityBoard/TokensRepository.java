package com.CityBoard;

import com.CityBoard.postgresql.dto.JwtToken;
import com.CityBoard.postgresql.repository.TokensJPARepository;
import org.springframework.stereotype.Component;

@Component
public class TokensRepository implements ITokensRepository {
    private final TokensJPARepository tokensJPARepository;

    public TokensRepository(TokensJPARepository tokensJPARepository) {
        this.tokensJPARepository = tokensJPARepository;
    }

    @Override
    public String findByUsername(String username) {
        JwtToken jwt = tokensJPARepository.findByUsername(username);
        if (jwt == null) {
            return "";
        }
        return jwt.getToken();
    }

    @Override
    public String findById(Long id) {
        JwtToken jwt = tokensJPARepository.findById(id).orElse(null);
        if (jwt == null) {
            return "";
        }
        return jwt.getToken();
    }

    @Override
    public boolean checkToken(String username) {
        JwtToken jwt = tokensJPARepository.findByUsername(username);
        if (jwt == null) {
            return true;
        }
        return false;
    }

    @Override
    public void saveToken(String username, String token) {
        JwtToken jwt = new JwtToken();
        jwt.setToken(token);
        jwt.setUsername(username);
        tokensJPARepository.save(jwt);
    }

    @Override
    public void updateToken(String username, String token) {
        JwtToken jwt = tokensJPARepository.findByUsername(username);
        jwt.setToken(token);
        tokensJPARepository.save(jwt);
    }
}
