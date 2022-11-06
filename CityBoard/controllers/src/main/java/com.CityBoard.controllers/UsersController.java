package com.CityBoard.controllers;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.ui.ClientUI;
import com.CityBoard.ui.NoRegUI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final NoRegUI noRegUI;
    private final ClientUI clientUI;

    public UserController(NoRegUI noRegUI, ClientUI clientUI) {
        this.noRegUI = noRegUI;
        this.clientUI = clientUI;
    }

    //@GetMapping("/")
    //public String showAdverts(Model model, Principal principal) {
    //    List<Adverts> adverts = null;
    //    if (noRegUI.isUserAuthenticated()) {
    //        adverts = clientUI.viewAvailableAdverts(noRegUI.getUserByPrincipal(principal));
    //    } else {
    //        adverts = clientUI.viewAvailableAdverts(null);
    //    }
    //    model.addAttribute("Adverts", adverts);
    //    return "index";
    //}
//
    //@GetMapping("/advert")
    //public String showAdvert(@RequestParam(value = "id") Long advertId, Principal principal, Model model) {
    //    Adverts advert = clientUI.viewAdvert(advertId);
    //    if (advert != null) {
    //        model.addAttribute("advert", advert);
    //        if (noRegUI.isUserAuthenticated()) {
    //            if (advert.getAuthor().getUsername().equals(principal.getName())) {
    //                model.addAttribute("userIsNotOwner", false);
    //            } else {
    //                model.addAttribute("userIsNotOwner", true);
    //            }
    //        }
    //        return "advert";
    //    }
    //    return "redirect:/";
    //}
//
    //@GetMapping("/registration")
    //public String showRegistrationForm(Model model) {
    //    model.addAttribute("UserCredentialsDTO", new UserDTO());
    //    return "registration";
    //}
//
    //@PostMapping("/register")
    //public String registerUser(UserDTO dto, BindingResult bindingResult, Model model) {
    //    Users user = noRegUI.registerUser(dto);
    //    if (user == null) {
    //        bindingResult.rejectValue("username", "Данное имя занято");
    //        model.addAttribute("UserCredentialsDTO", dto);
    //        return "registration";
    //    }
    //    return "redirect:/";
    //}
//
    //@GetMapping("/user/advert/create")
    //public String showAdvertCreationForm(Model model) {
    //    model.addAttribute("AdvertDTO", new AdvertDTO());
    //    return "create-advert";
    //}
//
    //@GetMapping("/user/advert/update")
    //public String showAdvertUpdateForm(@RequestParam(value = "id") Long advertId, Principal principal, Model model) {
    //    if (noRegUI.isUserAuthenticated()) {
    //        Users user = noRegUI.getUserByPrincipal(principal);
    //        if (user != null) {
    //            Adverts advert = clientUI.viewAdvert(advertId);
    //            if (advert != null) {
    //                AdvertDTO advertDTO = new AdvertDTO();
    //                advertDTO.mapEntity(advert);
    //                model.addAttribute("AdvertDTO", advertDTO);
    //                return "update-advert";
    //            }
    //        }
    //    }
    //    return "redirect:/user/adverts";
    //}
//
    //@PostMapping("/user/adverts/save")
    //public String saveAdvert(AdvertDTO dto, Principal principal, BindingResult bindingResult, Model model) {
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    if (user != null) {
    //        dto.setUser(user);
    //        Adverts advert = clientUI.createAdvert(dto);
    //        if (advert != null) {
    //            return "redirect:/";
    //        } else {
    //            bindingResult.reject("Невозможно создать объявление");
    //            model.addAttribute("AdvertDTO", dto);
    //            return "create-advert";
    //        }
    //    }
    //    return "redirect:/user/advert/create";
    //}
//
    //@GetMapping("user/adverts/update")
    //public String updateAdvert(@RequestParam(value = "id") Long advertId, AdvertDTO advertDTO, Principal principal) {
    //    Users user = noRegUI.getUserByPrincipal(principal);
    //    if (user != null) {
    //        Adverts advert = clientUI.updateAdvert(advertId, advertDTO);
    //        if (advert != null) {
    //            return "redirect:/";
    //        }
    //    }
    //    return "redirect:/user/advert/update?=" + advertId.toString();
    //}
//
    //@GetMapping("/user/adverts/hide")
    //public String hideAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
    //    if (noRegUI.isUserAuthenticated()) {
    //        Users user = noRegUI.getUserByPrincipal(principal);
    //        if (user != null) {
    //            Adverts advert = clientUI.viewAdvert(advertId);
    //            if (advert != null && advert.getAuthor().getUsername().equals(principal.getName())) {
    //                clientUI.hideAdvert(advertId);
    //            }
    //        }
    //    }
    //    return "redirect:/user/adverts";
    //}
//
    //@GetMapping("/user/adverts/delete")
    //public String deleteAdvert(@RequestParam(value = "id") Long advertId, Principal principal) {
    //    if (noRegUI.isUserAuthenticated()) {
    //        Users user = noRegUI.getUserByPrincipal(principal);
    //        if (user != null) {
    //            Adverts advert = clientUI.viewAdvert(advertId);
    //            if (advert != null && advert.getAuthor().getUsername().equals(principal.getName())) {
    //                clientUI.deleteAdvert(advertId);
    //            }
    //        }
    //    }
    //    return "redirect:/user/adverts";
    //}
//
    //@GetMapping("/user/requests")
    //public String showUserRequest(Principal principal, Model model) {
    //    if (noRegUI.isUserAuthenticated()) {
    //        Users user = noRegUI.getUserByPrincipal(principal);
    //        if (user != null) {
    //            List<Requests> requestsTo = clientUI.getIncomingRequests(user.getId());
    //            List<Requests> requestsFrom = clientUI.getOutgoingRequests(user.getId());
    //            model.addAttribute("requestsTo", requestsTo);
    //            model.addAttribute("requestsFrom", requestsFrom);
    //        }
    //    }
    //    return "requests";
    //}
//
    //@GetMapping("/user/request")
    //public String makeRequestToAdvert(@RequestParam(value = "type") RequestType requestType,
    //                                  @RequestParam(value = "id") Long advertId,
    //                                  Principal principal) {
    //    if (noRegUI.isUserAuthenticated()) {
    //        Users user = noRegUI.getUserByPrincipal(principal);
    //        if (user != null) {
    //            clientUI.makeRequest(user, advertId, requestType);
    //        }
    //    }
    //    return "redirect:/advert?id=" + advertId.toString();
    //}
//
    //@GetMapping("/request/accept")
    //public String acceptChosenRequest(@RequestParam(value = "id", required = true) Long requestId) {
    //    clientUI.acceptRequest(requestId);
    //    return "redirect:/user/requests";
    //}
//
    //@GetMapping("/request/reject")
    //public String rejectChosenRequest(@RequestParam(value = "id", required = true) Long requestId) {
    //    clientUI.rejectRequest(requestId);
    //    return "redirect:/user/requests";
    //}
}
