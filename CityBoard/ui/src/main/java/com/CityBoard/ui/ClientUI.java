package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.Roles;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dto.RequestDTO;
import com.CityBoard.postgresql.dto.UserDTO;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.RequestsService;
import com.CityBoard.ui.operations.ClientOperations;
import com.CityBoard.ui.operations.CommonOperations;
import com.CityBoard.ui.pagination.Paged;
import com.CityBoard.ui.pagination.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUI implements ClientOperations, CommonOperations {
    private final RequestsService requestsService;
    private final AdvertsService advertsService;

    public ClientUI(RequestsService requestsService, AdvertsService advertsService) {
        this.requestsService = requestsService;
        this.advertsService = advertsService;
    }

    @Override
    public boolean makeRequest(UserDTO user, Long advertId, RequestType type) {
        AdvertDTO advertDTO = advertsService.getAdvertDTOById(advertId);
        if (advertDTO != null) {
            RequestDTO requestDTO = requestsService.createRequest(user, advertDTO, type);
            if (requestDTO != null) {
                requestsService.save(requestDTO);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Requests> getIncomingRequests(Long userId) {
        return requestsService.getIncomingRequests(userId);
    }

    @Override
    public List<Requests> getOutgoingRequests(Long userId) {
        return requestsService.getOutgoingRequests(userId);
    }

    @Override
    public boolean acceptRequest(Long requestId) {
        return requestsService.changeRequestStatus(requestId, RequestStatus.ACCEPTED);
    }

    @Override
    public boolean rejectRequest(Long requestId) {
        return requestsService.changeRequestStatus(requestId, RequestStatus.REJECTED);
    }

    @Override
    public boolean createAdvert(Adverts advert) {
        AdvertDTO dto = advertsService.createAdvert(advert);
        if (dto != null) {
            advertsService.save(dto);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAdvert(Adverts advert) {
        AdvertDTO dto = advertsService.updateAdvert(advert);
        if (dto != null) {
            advertsService.save(dto);
            return true;
        }
        return false;
    }

    @Override
    public boolean hideAdvert(Long advertId) {
        return advertsService.changeAdvertStatus(advertId, AdvertStatus.HIDDEN);
    }

    @Override
    public boolean revealAdvert(Long advertId) {
        return advertsService.changeAdvertStatus(advertId, AdvertStatus.VISIBLE);
    }

    @Override
    public boolean deleteAdvert(Long advertId) {
        return advertsService.changeAdvertStatus(advertId, AdvertStatus.DELETED);
    }

    @Override
    public Paged<Adverts> getAvailableAdvertsPaged(Users user, int currentPage, int pageSize) {
        Page<Adverts> advertsPage = advertsService.getVisibleNotAuthoredAdvertsPage(user.getId(), currentPage, pageSize);
        return new Paged<>(advertsPage, Paging.of(advertsPage.getTotalPages(), currentPage, pageSize));
    }

    public List<Adverts> getUserAdverts(Long userId) {
        return advertsService.getAuthoredAdvertsList(userId);
    }

    @Override
    public Adverts getAdvert(Long advertId) {
        return advertsService.getAdvertById(advertId);
    }
}
