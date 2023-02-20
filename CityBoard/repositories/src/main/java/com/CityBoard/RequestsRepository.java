package com.CityBoard;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.postgresql.dto.RequestDTO;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.postgresql.repository.AdvertsJPARepository;
import com.CityBoard.postgresql.repository.RequestsJPARepository;
import com.CityBoard.postgresql.repository.UsersJPARepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class RequestsRepository implements IRequestsRepository {
    private final RequestsJPARepository requestsJPARepository;
    private final AdvertsJPARepository advertsJPARepository;
    private final UsersJPARepository usersJPARepository;

    public RequestsRepository(RequestsJPARepository requestsJPARepository, AdvertsJPARepository advertsJPARepository, UsersJPARepository usersJPARepository) {
        this.requestsJPARepository = requestsJPARepository;
        this.advertsJPARepository = advertsJPARepository;
        this.usersJPARepository = usersJPARepository;
    }

    @Override
    public Requests findById(Long id) {
        RequestDTO requestDTO = requestsJPARepository.findById(id).orElse(null);
        if (requestDTO != null) {
            return requestDTO.mapDTOtoEntity();
        }
        return null;
    }

    @Override
    public List<Requests> findIncoming(Long userId) {
        List<RequestDTO> requestDTOList = requestsJPARepository.findIncoming(userId);
        return mapDTOtoEntityList(requestDTOList);
    }

    @Override
    public List<Requests> findOutgoing(Long userId) {
        List<RequestDTO> requestDTOList = requestsJPARepository.findOutgoing(userId);
        return mapDTOtoEntityList(requestDTOList);
    }

    @Override
    public boolean createRequest(Long authorId, Long advertId, RequestType type) {
        UserDTO user = usersJPARepository.findById(authorId).orElse(null);
        AdvertDTO advert = advertsJPARepository.findById(advertId).orElse(null);
        if (user == null || advert == null) {
            return false;
        }
        else {
            RequestDTO request = RequestDTO.builder()
                    .user(user)
                    .advert(advert)
                    .status(RequestStatus.PENDING)
                    .type(type).build();
            requestsJPARepository.save(request);
            return true;
        }
    }

    @Override
    public boolean updateRequestStatus(Long requestId, RequestStatus status) {
        RequestDTO requestDTO = requestsJPARepository.findById(requestId).orElse(null);
        if (requestDTO != null) {
            requestDTO.setStatus(status);
            requestsJPARepository.save(requestDTO);
            return true;
        }
        return false;
    }

    private List<Requests> mapDTOtoEntityList(List<RequestDTO> dtoList) {
        List<Requests> requests = new ArrayList<>();
        for (RequestDTO dto : dtoList) {
            requests.add(dto.mapDTOtoEntity());
        }
        return requests;
    }

}
