package com.CityBoard.controllers;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.services.AdvertsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller("adverts")
public class AdvertController extends AbstractController<Adverts, AdvertsService> {

    protected AdvertController(AdvertsService service) {
        super(service);
    }

    @GetMapping("/cityboard")
    public String showMainPage(Model model) {
        model.addAttribute("adverts", service.getAllAdverts());
        return "city-board";
    }

    @GetMapping("/cityboard/{id}")
    public String showAdvertPage(@PathVariable("id") Long id, Model model) {
        Adverts advert = service.getAdvertById(id);
        if (advert != null) {
            model.addAttribute("advert", advert);
        }
        return "advert-page";
    }

    @GetMapping("/cityboard/edit/{id}")
    public String updateAdvert(@PathVariable("id") Long id, Model model) {
        return "update-advert";
    }

    @GetMapping("/cityboard/my-adverts")
    public String showUserAdverts(Principal principal, Model model) {
        List<Adverts> adverts = service.getUserAdverts(principal.getName());
        if (adverts != null) {
            model.addAttribute("adverts", adverts);
        }
        return "my-adverts";
    }

    @GetMapping("cityboard/my-adverts/edit/{id}")
    public String showAdvertUpdateForm (@PathVariable("id") Long id, Users user, Model model) {
        return "update-advert";
    }

    @GetMapping("/create-advert")
    public String showAdvertUpdateForm(Users user, BindingResult bindingResult, Model model) {
        model.addAttribute("Advert", new Adverts());
        return "create-advert";
    }

    @Override
    @PostMapping("/save-advert")
    public String create(Principal principal, Adverts entity) {
        service.createAdvert(principal.getName(), entity.getType(),
                             entity.getEmail(), entity.getPhone(),
                             entity.getPrice(), entity.getAddress(),
                             entity.getArea());
        return "redirect:/";
    }

    @GetMapping("/moderator/check")
    public String showAdvertListModerationForm() {
        return "city-board";
    }

}
