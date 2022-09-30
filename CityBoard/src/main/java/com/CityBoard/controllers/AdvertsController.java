package com.CityBoard.controllers;

import com.CityBoard.models.Adverts;
import com.CityBoard.services.AdvertsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AdvertsController extends AbstractController<Adverts, AdvertsService> {
    protected AdvertsController(AdvertsService service) {
        super(service);
    }

    @GetMapping("/")
    public String showAllAdverts(Principal principal) {
        List<Adverts> adverts = service.getAvailableAdverts(principal);
        for (Adverts advert : adverts) {
            System.out.println("advert" + advert.getId().toString() +
                    ": status = " + advert.getStatus().toString() +
                    ", type = " + advert.getType());
        }
        return "blank";
    }
}
