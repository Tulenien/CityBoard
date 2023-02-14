package com.CityBoard.ui.operations;

import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.ui.pagination.Paged;

import java.util.List;

public interface AdminOperations {
    Paged<Users> getUsersPaged(int currentPage, int pageSize);

    boolean addUserRole(Long userId, Roles role);

    boolean removeUserRole(Long userId, Roles role);
}
