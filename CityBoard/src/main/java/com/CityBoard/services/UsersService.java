package com.CityBoard.services;

//import com.CityBoard.dto.UserProfileDTO;

import com.CityBoard.dto.UserEncodedDTO;
import com.CityBoard.models.Users;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService extends AbstractService<Users, UsersRepository> {

    public UsersService(UsersRepository repository) {
        super(repository);
    }

    public Users getUserByUsername(String username) {
        return repository.findByUsername(username);
    }
    public Users createUserFromEncodedDTO(UserEncodedDTO userEncoded) {
        Users user = Users.builder()
                .username(userEncoded.getUsername())
                .password(userEncoded.getEncodedPassword())
                .status(userEncoded.getUserStatus())
                .password_expired(userEncoded.isPassword_expired())
                .build();
        return user;
    }

    @Override
    public void save(Users entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Users entity) {
        repository.delete(entity);
    }


}
