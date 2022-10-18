package com.CityBoard.rest;

import com.CityBoard.models.Adverts;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdvertController {
    @GetMapping("/adverts")
    public List<Adverts> showAdverts(Model model, Principal principal) {
        List<Adverts> adverts = new ArrayList<>();
        adverts.add(new Adverts());
        return adverts;
    }
}
