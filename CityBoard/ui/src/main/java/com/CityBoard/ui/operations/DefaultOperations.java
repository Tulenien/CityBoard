package com.CityBoard.ui.operations;

import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dto.UserDTO;

import java.security.Principal;

public interface DefaultOperations {
    boolean isUserAuthenticated();

    Users getUserByPrincipal(Principal principal);

    boolean registerUser(Users user) throws Exception;
}
