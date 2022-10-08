package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.repositories.RequestsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestsService extends AbstractService<Requests, RequestsRepository> {
    private static final Logger logger = LoggerFactory.getLogger(RequestsService.class);
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
        save(request);
        return request;
    }

    public List<Requests> getIncomingRequests(Users user) {
        return repository.findIncoming(user.getId());
    }

    public List<Requests> getOutgoingRequests(Users user) {
        return repository.findOutgoing(user.getId());
    }

    public Requests getRequestById(Long requestId) {
        return repository.findById(requestId).orElse(null);
    }

    public void acceptRequest(Requests request) {
        logger.info("Request {} accepted", request.getId());
        request.setStatus(RequestStatus.ACCEPTED);
        save(request);
    }

    public void rejectRequest(Requests request) {
        logger.info("Request {} rejected", request.getId());
        request.setStatus(RequestStatus.REJECTED);
        save(request);
    }

    @Override
    public void save(Requests entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Requests entity) {
        repository.delete(entity);
    }
}
