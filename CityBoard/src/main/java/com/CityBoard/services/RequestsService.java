package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.repositories.RequestsRepository;

public class RequestsService extends AbstractService<Requests, RequestsRepository> {
    public RequestsService(RequestsRepository repository) {
        super(repository);
    }

    public Requests createRequest(Users author, Adverts advert, RequestType type) {
        Requests request = Requests.builder()
                .type(type)
                .status(RequestStatus.PENDING)
                .user(author)
                .advert(advert)
                .build();
        return request;
    }

    public void acceptRequest(Requests request) {
        request.setStatus(RequestStatus.ACCEPTED);
    }

    public void rejectRequest(Requests request) {
        request.setStatus(RequestStatus.REJECTED);
    }

    @Override
    public void save(Requests entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Requests entity) {
        repository.save(entity);
    }
}
