package com.CityBoard.controllers;

import com.CityBoard.models.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdvertController {
    @GetMapping("/cityboard")
    public String showMainPage(Model model) {
        return "city-board";
    }

    @GetMapping("/cityboard/{id}")
    public String showAdvertPage(@PathVariable("id") Long id, Model model) {
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

    @GetMapping("/moderator/check")
    public String showAdvertListModerationForm() {
        return "city-board";
    }
}
