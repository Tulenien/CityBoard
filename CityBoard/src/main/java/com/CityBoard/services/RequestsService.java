package com.CityBoard.services;

import com.CityBoard.models.Requests;
import com.CityBoard.repositories.RequestsRepository;

public class RequestsService extends AbstractService<Requests, RequestsRepository> {
    public RequestsService(RequestsRepository repository) {
        super(repository);
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
