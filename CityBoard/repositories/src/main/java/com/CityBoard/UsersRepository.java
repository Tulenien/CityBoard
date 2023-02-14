package com.CityBoard;

import com.CityBoard.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UsersRepository implements IUsersRepository {
    @Override
    public Page<Users> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Users findByUsername(String username) {
        return null;
    }

    @Override
    public Users findById(Long id) {
        return null;
    }
}
