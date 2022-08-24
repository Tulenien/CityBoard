package com.CityBoard.services;

import com.CityBoard.DTO.AdvertDTO;
import com.CityBoard.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AdvertsService {
    public final RequestsService requestsService;

    @Autowired
    public AdvertsService(RequestsService requestsService) {
        this.requestsService = requestsService;
    }

    public Advert createAdvert(Users author, AdvertDTO object) {
        Advert advert = Advert.builder()
                .email(object.getEmail())
                .phone(object.getPhone())
                .price(object.getPrice())
                .address(object.getAddress())
                .area(object.getArea())
                .type(object.getType())
                .user(author)
                .status(AdvertStatus.VISIBLE)
                .mod_check(false)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();
        return advert;
    }

    public Advert updateAdvert(AdvertDTO redacted, Advert toUpdate) {
        toUpdate.setType(redacted.getType());
        toUpdate.setEmail(redacted.getEmail());
        toUpdate.setPhone(redacted.getPhone());
        toUpdate.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        toUpdate.setStatus(redacted.getStatus());
        toUpdate.setMod_check(false);
        toUpdate.setAddress(redacted.getAddress());
        toUpdate.setPrice(redacted.getPrice());
        toUpdate.setArea(redacted.getArea());
        return toUpdate;
    }

    public void makeRequest(Users requester, Advert advert, RequestType requestType) {
        Request request = requestsService.createRequest(requestType, requester, advert);
    }
}
