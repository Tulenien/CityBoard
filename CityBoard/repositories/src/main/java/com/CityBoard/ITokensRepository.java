package com.CityBoard;

public interface ITokensRepository {
    String findByUsername(String username);
    String findById(Long id);
    boolean checkToken(String username);
    void saveToken(String username, String token);
    void updateToken(String username, String token);
}
