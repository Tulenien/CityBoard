package com.CityBoard.services;

import com.CityBoard.DTO.UserProfileDTO;
import com.CityBoard.models.Users;
import com.CityBoard.repositories.UserRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Use the Repository
    public List<Users> getUsersList() {
        List<Users> users = userRepository.findAll();
        return users;
    }

    public UserProfileDTO getUserProfile(String username) {
        Users user = userRepository.findByUsername(username);
        UserProfileDTO userDTO = UserProfileDTO.builder()
                .username(username)
                .full_name(user.getFull_name())
                .created_at(user.getCreated_at())
                .roles(user.getRoles())
                .build();
        return userDTO;
    }
}
