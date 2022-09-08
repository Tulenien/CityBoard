package com.CityBoard.services;

//import com.CityBoard.dto.UserProfileDTO;
import com.CityBoard.models.Users;
import com.CityBoard.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService extends AbstractService<Users, UsersRepository> {

    public UsersService(UsersRepository repository) {
        super(repository);
    }

    // Use the Repository
    public List<Users> getUsersList() {
        List<Users> users = repository.findAll();
        return users;
    }

    //public UserProfileDTO getUserProfile(String username) {
    //    Users user = repository.findByUsername(username);
    //    UserProfileDTO userDTO = UserProfileDTO.builder()
    //            .username(username)
    //            .full_name(user.getFull_name())
    //            .created_at(user.getCreated_at())
    //            .roles(user.getRoles())
    //            .build();
    //    return userDTO;
    //}

    @Override
    public void save(Users entity) {
        repository.save(entity);
    }
}
