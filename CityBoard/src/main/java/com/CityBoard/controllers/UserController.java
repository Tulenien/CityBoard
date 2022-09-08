package com.CityBoard.controllers;

import com.CityBoard.models.Users;
import com.CityBoard.services.CustomUserDetailsService;
import com.CityBoard.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("/user")
public class UserController extends AbstractController<Users, UsersService> {
    private final CustomUserDetailsService userDetailsService;

    protected UserController(UsersService service, CustomUserDetailsService userDetailsService) {
        super(service);
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        //model.addAttribute("userCredentialsDTO", new UserCredentialsDTO());
        return "registration";
    }

    //@PostMapping("/register")
    //public String registerUser(UserCredentialsDTO userCredentials, BindingResult bindingResult, Model model) {
    //    if (bindingResult.hasErrors()) {
    //        model.addAttribute("registrationForm", userCredentials);
    //        return "registration";
    //    }
    //    try {
    //        userDetailsService.registerUser(userCredentials);
    //    }
    //    catch (Exception exception) {
    //        bindingResult.rejectValue("username", "userCredentialsDTO.username",
    //                "This username already exists");
    //        model.addAttribute("registrationForm", userCredentials);
    //        return "registration";
    //    }
    //    return "redirect:/login";
    //}

    @GetMapping("/admin/users")
    public String showUserList(Model model) {
        return "index";
    }

    @GetMapping("/admin/users/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        return "update-user";
    }

    @GetMapping("/profile")
    public String showUserProfile(Users user, Model model) {
        return "profile";
    }
}
