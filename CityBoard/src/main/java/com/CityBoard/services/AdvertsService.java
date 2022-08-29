package com.CityBoard.services;

import com.CityBoard.models.*;
import com.CityBoard.repositories.AdvertRepository;
import com.CityBoard.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertsService {
    public final UserRepository userRepository;
    public final AdvertRepository advertRepository;

    public AdvertsService(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }
    public Advert createAdvert(String authorName, AdvertType advertType, String email,
                               String phone, Integer price, String address,
                               Float area) {
        Users user = userRepository.findByUsername(authorName);
        Advert advert = Advert.builder()
                .type(advertType)
                .email(email)
                .phone(phone)
                .price(price)
                .address(address)
                .area(area)
                .user(user)
                .status(AdvertStatus.VISIBLE)
                .mod_check(false)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();
        user.addAdvert(advert);
        advertRepository.save(advert);
        userRepository.save(user);
        return advert;
    }

    public Advert updateAdvert(Advert toUpdate, AdvertType advertType, String email,
                               String phone, Integer price, String address,
                               Float area, AdvertStatus advertStatus) {
        toUpdate.setType(advertType);
        toUpdate.setEmail(email);
        toUpdate.setPhone(phone);
        // Updates automatically???
        toUpdate.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        toUpdate.setStatus(advertStatus);
        toUpdate.setMod_check(false);
        toUpdate.setAddress(address);
        toUpdate.setPrice(price);
        toUpdate.setArea(area);
        return toUpdate;
    }

    public void checkAdvert(Advert toCheck) {
        toCheck.setMod_check(true);
    }

    public void hideAdvert(Advert toHide) {
        toHide.setStatus(AdvertStatus.HIDDEN);
    }

    public void deleteAdvert(Advert toDelete) {
        toDelete.setStatus(AdvertStatus.DELETED);
    }

    public List<Advert> getAllAdverts() {
        return advertRepository.findAll();
    }

    public Advert getAdvertById(Long id) {
        return advertRepository.findById(id).orElse(null);
    }

    public List<Advert> getUserAdverts(String authorName) {
        return advertRepository.findByAuthor(authorName);
    }

}
