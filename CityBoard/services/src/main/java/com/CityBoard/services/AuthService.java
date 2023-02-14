package com.CityBoard.services;

import com.CityBoard.models.JwtAuthentication;
import com.CityBoard.models.JwtRequest;
import com.CityBoard.models.JwtResponse;
import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dto.JwtToken;
import com.CityBoard.postgresql.repository.TokensRepository;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokensRepository tokensRepository;
    private final UsersService userService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final Users user = userService.getUserByUsername(authRequest.getLogin());
        if (user == null) {
            throw new AuthException("Пользователь не найден");
        }
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            // save to database
            if (tokensRepository.findByUsername(user.getUsername()) == null) {
                tokensRepository.save(JwtToken.builder()
                                .username(user.getUsername())
                                .token(refreshToken)
                        .build());
            }
            else {
                JwtToken token = tokensRepository.findByUsername(user.getUsername());
                token.setToken(refreshToken);
                tokensRepository.save(token);
            }

            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            // Load token
            final String saveRefreshToken = tokensRepository.findByUsername(login).getToken();
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Users user = userService.getUserByUsername(login);
                if (user == null) {
                    throw new AuthException("Пользователь не найден");
                }
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            // Load token
            final String saveRefreshToken = tokensRepository.findByUsername(login).getToken();
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Users user = userService.getUserByUsername(login);
                if (user == null) {
                    throw new AuthException("Пользователь не найден");
                }
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                // Refresh token
                JwtToken token = tokensRepository.findByUsername(user.getUsername());
                token.setToken(refreshToken);
                tokensRepository.save(token);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new IllegalStateException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        }
        else {
            return null;
        }
    }
}
