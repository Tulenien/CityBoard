package com.CityBoard.controllers;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.ui.ClientUI;
import com.CityBoard.ui.NoRegUI;
import com.CityBoard.ui.pagination.Paged;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UsersController {
    private final NoRegUI noRegUI;
    private final ClientUI clientUI;

    public UsersController(NoRegUI noRegUI, ClientUI clientUI) {
        this.noRegUI = noRegUI;
        this.clientUI = clientUI;
    }

    @GetMapping("/")
    public String showAdverts(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int currentPage,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                              Model model, Principal principal) {
        Paged<Adverts> advertsPaged;
        if (noRegUI.isUserAuthenticated()) {
            advertsPaged = clientUI.getAvailableAdvertsPaged(noRegUI.getUserByPrincipal(principal), currentPage, pageSize);
        } else {
            advertsPaged = noRegUI.getAvailableAdvertsPaged(null, currentPage, pageSize);
        }
        model.addAttribute("AdvertsPage", advertsPaged);
        return "index";
    }

    @GetMapping("/advert")
    public String showAdvert(@RequestParam(value = "id") Long advertId, Principal principal, Model model) {
        Adverts advert = clientUI.getAdvert(advertId);
        if (advert != null) {
            model.addAttribute("advert", advert);
            Users user = noRegUI.getUserByPrincipal(principal);
            if (user != null && advert.getAuthorId().equals(user.getId())) {
                model.addAttribute("userIsNotOwner", false);
            }
            else {
                model.addAttribute("userIsNotOwner", true);
            }
            return "advert";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("UserCredentialsDTO", new Users());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(Users user, BindingResult bindingResult, Model model) {
        boolean status = noRegUI.registerUser(user);
        if (status == false) {
            bindingResult.rejectValue("username", "Данное имя занято");
            model.addAttribute("UserCredentialsDTO", user);
            return "registration";
        }
        return "redirect:/";
    }
    @GetMapping("/user/advert/create")
    public String showAdvertCreationForm(Model model) {
        model.addAttribute("AdvertDTO", new Adverts());
        return "create-advert";
    }

    @GetMapping("/user/advert/update")
    public String showAdvertUpdateForm(@RequestParam(value = "id") Long advertId, Principal principal, Model model) {
        if (noRegUI.isUserAuthenticated()) {
            Users user = noRegUI.getUserByPrincipal(principal);
            if (user != null) {
                Adverts advert = clientUI.getAdvert(advertId);
                if (advert != null && user.getId().equals(advert.getAuthorId())) {
                    model.addAttribute("AdvertDTO", advert);
                    return "update-advert";
                }
            }
        }
        return "redirect:/user/adverts";
    }

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
