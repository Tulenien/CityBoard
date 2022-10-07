package com.CityBoard.ui;

import com.CityBoard.models.Users;
import com.CityBoard.models.dto.UserCredentialsDTO;
import com.CityBoard.services.UsersService;
import com.CityBoard.ui.operations.DefaultOperations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class NoRegUI implements DefaultOperations {
    private final UsersService usersService;

    public NoRegUI(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean isUserAuthenticated() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return true;
        }
        return false;
    }

    @Override
    public Users getUserByPrincipal(Principal principal) {
        if (isUserAuthenticated()) {
            return usersService.getUserByUsername(principal.getName());
        }
        return null;
    }

    @Override
    public Users registerUser(UserCredentialsDTO userCredentials) {
        Users user;
        try {
            user = usersService.registerUser(userCredentials);
        } catch (Exception e) {
            return null;
        }
        return user;
    }
}
