package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.ui.operations.CommonOperations;
import com.CityBoard.ui.operations.ModOperations;
import com.CityBoard.ui.pagination.Paged;
import com.CityBoard.ui.pagination.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ModUI implements ModOperations, CommonOperations {
    private final AdvertsService advertsService;

    public ModUI(AdvertsService advertsService) {
        this.advertsService = advertsService;
    }

    @Override
    public boolean changeAdvertModCheck(Long advertId) {
        return advertsService.changeAdvertModCheck(advertId);
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
