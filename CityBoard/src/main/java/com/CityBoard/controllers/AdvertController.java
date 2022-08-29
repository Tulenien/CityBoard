package com.CityBoard.controllers;

import com.CityBoard.models.Advert;
import com.CityBoard.models.Users;
import com.CityBoard.services.AdvertsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AdvertController {
    private final AdvertsService advertsService;

    public AdvertController(AdvertsService advertsService) {
        this.advertsService = advertsService;
    }

    @GetMapping("/cityboard")
    public String showMainPage(Model model) {
        model.addAttribute("adverts", advertsService.getAllAdverts());
        return "city-board";
    }

    @GetMapping("/cityboard/{id}")
    public String showAdvertPage(@PathVariable("id") Long id, Model model) {
        Advert advert = advertsService.getAdvertById(id);
        if (advert != null) {
            model.addAttribute("advert", advert);
        }
        return "advert-page";
    }

    @GetMapping("/cityboard/edit/{id}")
    public String updateAdvert(@PathVariable("id") Long id, Model model) {
        return "update-advert";
    }

    @GetMapping("/my-adverts")
    public String showUserAdverts(Users user, Model model) {
        return "my-adverts";
    }

    @GetMapping("/my-adverts/edit/{id}")
    public String showAdvertUpdateForm (@PathVariable("id") Long id, Users user, Model model) {
        return "update-advert";
    }

    @GetMapping("/create-advert")
    public String showAdvertUpdateForm(Users user, BindingResult bindingResult, Model model) {
        model.addAttribute("Advert", new Advert());
        return "create-advert";
    }

    @PostMapping("/create-advert")
    public String createAdvert(Principal principal, Advert advert) {
        advertsService.createAdvert(principal.getName(), advert.getType(),
                advert.getEmail(), advert.getPhone(),
                advert.getPrice(), advert.getAddress(),
                advert.getArea());
        return "redirect:/cityboard";
    }

    @GetMapping("/moderator/check")
    public String showAdvertListModerationForm() {
        return "city-board";
    }
}
