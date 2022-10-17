package com.CityBoard.ui.operations;

import com.CityBoard.models.enums.Roles;

public interface AdminOperations {
    void deleteAdvertForever(Long advertId);

    void addUserRole(Long userId, Roles role);

    void removeUserRole(Long userId, Roles role);
}
