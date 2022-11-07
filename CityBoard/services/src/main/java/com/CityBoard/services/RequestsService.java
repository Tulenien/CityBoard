package com.CityBoard.services;

import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dbmodels.AdvertsModel;
import com.CityBoard.postgresql.dbmodels.RequestsModel;
import com.CityBoard.postgresql.dbmodels.UsersModel;
import com.CityBoard.postgresql.repository.RequestsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestsService extends AbstractService<RequestsModel, RequestsRepository> {
    public RequestsService(RequestsRepository repository) {
        super(repository);
    }

    public RequestsModel createRequest(UsersModel user, AdvertsModel advert, RequestType type) {
        return RequestsModel.builder()
                .type(type)
                .status(RequestStatus.PENDING)
                .user(user)
                .advert(advert)
                .build();
    }

    public List<Requests> getIncomingRequests(Long userId) {
        List<RequestsModel> requests = repository.findIncoming(userId);
        if (requests.isEmpty()) {
            return null;
        }
        return mapDTOtoEntityList(requests);
    }

    public List<Requests> getOutgoingRequests(Long userId) {
        List<RequestsModel> requests = repository.findOutgoing(userId);
        if (requests.isEmpty()) {
            return null;
        }
        return mapDTOtoEntityList(requests);
    }

    public Requests getRequestById(Long requestId) {
        RequestsModel request = repository.findById(requestId).orElse(null);
        if (request != null) {
            return request.mapDTOtoEntity();
        }
        return null;
    }

    public RequestsModel getRequestDTOById(Long requestId) {
        return repository.findById(requestId).orElse(null);
    }

    public boolean changeRequestStatus(Long requestId, RequestStatus status) {
        RequestsModel request = repository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus(status);
            save(request);
            return true;
        }
        return false;
    }

    private List<Requests> mapDTOtoEntityList(List<RequestsModel> dtoList) {
        List<Requests> requestsList = new ArrayList<>();
        for (RequestsModel dto : dtoList) {
            requestsList.add(dto.mapDTOtoEntity());
        }
        return requestsList;
    }

    @Override
    public void save(RequestsModel entity) {
        repository.save(entity);
    }

    @Override
    public void delete(RequestsModel entity) {
        repository.delete(entity);
    }
}
