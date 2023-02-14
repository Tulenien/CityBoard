package com.CityBoard.services;

import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.postgresql.dto.RequestDTO;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.postgresql.repository.RequestsJPARepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestsService extends AbstractService<RequestDTO, RequestsJPARepository> {
    public RequestsService(RequestsJPARepository repository) {
        super(repository);
    }

    public RequestDTO createRequest(UserDTO user, AdvertDTO advert, RequestType type) {
        RequestDTO request = RequestDTO.builder()
                .type(type)
                .status(RequestStatus.PENDING)
                .user(user)
                .advert(advert)
                .build();
        save(request);
        return request;
    }

    public List<Requests> getIncomingRequests(Long userId) {
        List<RequestDTO> requests = repository.findIncoming(userId);
        if (requests.isEmpty()) {
            return null;
        }
        return mapDTOtoEntityList(requests);
    }

    public List<Requests> getOutgoingRequests(Long userId) {
        List<RequestDTO> requests = repository.findOutgoing(userId);
        if (requests.isEmpty()) {
            return null;
        }
        return mapDTOtoEntityList(requests);
    }

    public Requests getRequestById(Long requestId) {
        RequestDTO request = repository.findById(requestId).orElse(null);
        if (request != null) {
            return request.mapDTOtoEntity();
        }
        return null;
    }

    public RequestDTO getRequestDTOById(Long requestId) {
        return repository.findById(requestId).orElse(null);
    }

    public boolean changeRequestStatus(Long requestId, RequestStatus status) {
        RequestDTO request = repository.findById(requestId).orElse(null);
        if (request != null) {
            request.setStatus(status);
            save(request);
            return true;
        }
        return false;
    }

    private List<Requests> mapDTOtoEntityList(List<RequestDTO> dtoList) {
        List<Requests> requestsList = new ArrayList<>();
        for (RequestDTO dto : dtoList) {
            requestsList.add(dto.mapDTOtoEntity());
        }
        return requestsList;
    }

    @Override
    public void save(RequestDTO entity) {
        repository.save(entity);
    }

    @Override
    public void delete(RequestDTO entity) {
        repository.delete(entity);
    }
}
