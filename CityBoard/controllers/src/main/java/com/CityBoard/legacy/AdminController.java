package com.CityBoard.controllers;

import com.CityBoard.ui.AdminUI;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    private final AdminUI ui;

    public AdminController(AdminUI ui) {
        this.ui = ui;
    }

    //@GetMapping("/admin/users")
    //public String showUsers(Model model) {
    //    List<Users> users = ui.getUsers();
    //    model.addAttribute("Users", users);
    //    return "users";
    //}
//
    //@GetMapping("/admin/remove/{id}/{role}")
    //public String removeUserRole(@PathVariable("id") Long id,
    //                             @PathVariable("role") Roles role) {
    //    ui.removeUserRole(id, role);
    //    return "redirect:/admin/users";
    //}
//
    //@GetMapping("/admin/add/{id}/{role}")
    //public String addUserRole(@PathVariable("id") Long id,
    //                          @PathVariable("role") Roles role) {
    //    ui.addUserRole(id, role);
    //    return "redirect:/admin/users";
    //}
}
