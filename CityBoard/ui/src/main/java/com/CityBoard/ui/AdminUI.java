package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dto.RequestDTO;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.RequestsService;
import com.CityBoard.services.UsersService;
import com.CityBoard.ui.operations.AdminOperations;
import com.CityBoard.ui.operations.CommonOperations;
import com.CityBoard.ui.pagination.Paged;
import com.CityBoard.ui.pagination.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUI implements AdminOperations, CommonOperations {
    private final AdvertsService advertsService;
    private final RequestsService requestsService;
    private final UsersService usersService;

    public AdminUI(AdvertsService advertsService, RequestsService requestsService, UsersService usersService) {
        this.advertsService = advertsService;
        this.requestsService = requestsService;
        this.usersService = usersService;
    }

    @Override
    public Paged<Users> getUsersPaged(int currentPage, int pageSize) {
        Page<Users> usersPage = usersService.getAllUsersPage(currentPage, pageSize);
        return new Paged<>(usersPage, Paging.of(usersPage.getTotalPages(), currentPage, pageSize));
    }

    @Override
    public void addUserRole(Long userId, Roles role) {
        usersService.addRole(userId, role);
    }
    @Override
    public void removeUserRole(Long userId, Roles role) {
        usersService.removeRole(userId, role);
    }

    @Override
    public Paged<Adverts> getAvailableAdvertsPaged(Users user, int currentPage, int pageSize) {
        Page<Adverts> advertsPage = advertsService.getAllAdvertsPage(currentPage, pageSize);
        return new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
    }

    @Override
    public Adverts getAdvert(Long advertId) {
        return advertsService.getAdvertById(advertId);
    }
}
