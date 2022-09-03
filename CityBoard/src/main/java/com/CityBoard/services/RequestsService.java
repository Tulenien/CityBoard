package com.CityBoard.services;

import com.CityBoard.models.*;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.repositories.RequestsRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestsService extends AbstractService<Requests, RequestsRepository> {

    public RequestsService(RequestsRepository repository) {
        super(repository);
    }

    public Requests createRequest(RequestType requestType, Users author, Adverts advert) {
        Requests request = Requests.builder()
                .type(requestType)
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
}
