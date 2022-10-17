package com.CityBoard.ui.operations;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;

import java.util.List;

public interface AdminOperations {
    List<Users> getUsers();
    void deleteAdvertForever(Long advertId);

    void addUserRole(Long userId, Roles role);

    void removeUserRole(Long userId, Roles role);
}
