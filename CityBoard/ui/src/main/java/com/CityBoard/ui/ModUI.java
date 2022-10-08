package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.ui.operations.ModOperations;
import org.springframework.stereotype.Service;

@Service
public class ModUI implements ModOperations {
    private final AdvertsService advertsService;

    public ModUI(AdvertsService advertsService) {
        this.advertsService = advertsService;
    }

    @Override
    public void checkAdvert(Long advertId) {
        Adverts advert = advertsService.getAdvertById(advertId);
        if (advert != null) {
            advertsService.doModeratorCheck(advert);
        }
    }
}
