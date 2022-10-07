package com.CityBoard.ui.operations;

import com.CityBoard.models.Users;
import com.CityBoard.models.dto.UserCredentialsDTO;

import java.security.Principal;

public interface DefaultOperations {
    boolean isUserAuthenticated();

    Users getUserByPrincipal(Principal principal);

    Users registerUser(UserCredentialsDTO userCredentials) throws Exception;
}
