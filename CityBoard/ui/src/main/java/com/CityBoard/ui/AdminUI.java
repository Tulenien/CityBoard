package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.RequestsService;
import com.CityBoard.services.UsersService;
import com.CityBoard.ui.operations.AdminOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUI implements AdminOperations {
    private final AdvertsService advertsService;
    private final RequestsService requestsService;
    private final UsersService usersService;

    public AdminUI(AdvertsService advertsService, RequestsService requestsService, UsersService usersService) {
        this.advertsService = advertsService;
        this.requestsService = requestsService;
        this.usersService = usersService;
    }

    @Override
    public List<Users> getUsers() {
        return usersService.getUsersList();
    }

    @Override
    public void deleteAdvertForever(Long advertId) {
        Adverts advert = advertsService.getAdvertById(advertId);
        if (advert != null) {
            List<Requests> requests = advert.getRequests();
            for (Requests request : requests) {
                requestsService.delete(request);
            }
            advertsService.deleteAdvertPermanently(advert);
        }
    }

    @Override
    public void addUserRole(Long userId, Roles role) {
        Users user = usersService.getUserById(userId);
        if (user != null) {
            usersService.addRole(user, role);
        }
    }

    @Override
    public void removeUserRole(Long userId, Roles role) {
        Users user = usersService.getUserById(userId);
        if (user != null) {
            usersService.removeRole(user, role);
        }
    }
}
