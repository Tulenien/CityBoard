package com.CityBoard.controllers;

import com.CityBoard.services.Populator;
import com.CityBoard.ui.AdminUI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    private final AdminUI ui;
    private final Populator populator;

    public AdminController(AdminUI ui, Populator populator) {
        this.ui = ui;
        this.populator = populator;
    }

    @GetMapping("/populate")
    public String populateDatabase()
    {
        populator.populateDatabase();
        return "redirect:/index";
    }
}
