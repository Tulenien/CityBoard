package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dbmodels.UsersModelImpl;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.UsersService;
import com.CityBoard.ui.operations.CommonOperations;
import com.CityBoard.ui.operations.DefaultOperations;
import com.CityBoard.ui.pagination.Paged;
import com.CityBoard.ui.pagination.Paging;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class NoRegUI implements DefaultOperations, CommonOperations {
    private final UsersService usersService;
    private final AdvertsService advertsService;

    public NoRegUI(UsersService usersService, AdvertsService advertsService) {
        this.usersService = usersService;
        this.advertsService = advertsService;
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
    public UsersModelImpl getUserDTOByPrincipal(Principal principal) {
        if (isUserAuthenticated()) {
            return usersService.getUserDTOByUsername(principal.getName());
        }
        return null;
    }

    @Override
    public boolean registerUser(Users user) {
        try {
            UsersModelImpl dto = usersService.createUser(user);
            usersService.save(dto);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Paged<Adverts> getAvailableAdvertsPaged(Users user, int currentPage, int pageSize) {
        Page<Adverts> advertsPage = advertsService.getNotDeletedAdvertsPage(currentPage, pageSize);
        return new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
    }

    @Override
    public Adverts getAdvert(Long advertId) {
        return advertsService.getAdvertById(advertId);
    }
}
