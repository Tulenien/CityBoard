package com.CityBoard.services;

import com.CityBoard.models.*;

import java.sql.Timestamp;

public class RequestsService {
    public Request createRequest(RequestType requestType, Users author, Advert advert) {
        Request request = Request.builder()
                .type(requestType)
                .status(RequestStatus.PENDING)
                .created_at(new Timestamp(System.currentTimeMillis()))
                .user(author)
                .advert(advert)
                .build();
        return request;
    }

    public void acceptRequest(Request request) {
        request.setStatus(RequestStatus.ACCEPTED);
    }

    public void rejectRequest(Request request) {
        request.setStatus(RequestStatus.REJECTED);
    }
}
