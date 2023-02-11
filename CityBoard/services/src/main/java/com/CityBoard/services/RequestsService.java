package com.CityBoard.services;

import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dbmodels.AdvertsPostgres;
import com.CityBoard.postgresql.dbmodels.RequestsPostgres;
import com.CityBoard.postgresql.dbmodels.UsersPostgres;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestsService extends AbstractService<RequestsPostgres, RequestsRepository> {
    public RequestsService(RequestsRepository repository) {
        super(repository);
    }

    public RequestsPostgres createRequest(UsersPostgres user, AdvertsPostgres advert, RequestType type) {
        return RequestsPostgres.builder()
                .type(type)
                .status(RequestStatus.PENDING)
                .user(user)
                .advert(advert)
                .build();
    }

    public List<Requests> getIncomingRequests(Long userId) {
        List<RequestsPostgres> requests = repository.findIncoming(userId);
        if (requests.isEmpty()) {
            return null;
        }
        return mapDTOtoEntityList(requests);
    }

    public List<Requests> getOutgoingRequests(Long userId) {
        List<RequestsPostgres> requests = repository.findOutgoing(userId);
        if (requests.isEmpty()) {
            return null;
        }
        return mapDTOtoEntityList(requests);
    }

    public Requests getRequestById(Long requestId) {
        RequestsPostgres request = repository.findById(requestId).orElse(null);
        if (request != null) {
            return request.mapDTOtoEntity();
        }
        return null;
    }

    public RequestsPostgres getRequestDTOById(Long requestId) {
        return repository.findById(requestId).orElse(null);
    }

    public boolean changeRequestStatus(Long requestId, RequestStatus status) {
        RequestsPostgres request = repository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus(status);
            save(request);
            return true;
        }
        return false;
    }

    private List<Requests> mapDTOtoEntityList(List<RequestsPostgres> dtoList) {
        List<Requests> requestsList = new ArrayList<>();
        for (RequestsPostgres dto : dtoList) {
            requestsList.add(dto.mapDTOtoEntity());
        }
        return requestsList;
    }

    @Override
    public void save(RequestsPostgres entity) {
        repository.save(entity);
    }

    @Override
    public void delete(RequestsPostgres entity) {
        repository.delete(entity);
    }
}
