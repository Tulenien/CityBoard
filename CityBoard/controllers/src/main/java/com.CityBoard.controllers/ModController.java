package com.CityBoard.controllers;

import com.CityBoard.ui.ModUI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModController {
    private final ModUI ui;

    public ModController(ModUI ui) {
        this.ui = ui;
    }

    //@GetMapping("/mod/check")
    //public String checkAdvert(@RequestParam(value = "id") Long advertId) {
    //    ui.checkAdvert(advertId);
    //    return "redirect:/";
    //}
}
