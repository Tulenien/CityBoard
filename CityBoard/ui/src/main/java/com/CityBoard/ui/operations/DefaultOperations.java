package com.CityBoard.ui.operations;

import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dbmodels.UsersModel;

import java.security.Principal;

public interface DefaultOperations {
    boolean isUserAuthenticated();

    UsersModel getUserDTOByPrincipal(Principal principal);

    Users getUserByPrincipal(Principal principal);

    boolean registerUser(Users user) throws Exception;
}
