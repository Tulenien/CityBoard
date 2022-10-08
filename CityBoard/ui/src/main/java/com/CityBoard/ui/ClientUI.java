package com.CityBoard.ui;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.dto.AdvertDTO;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.services.AdvertsService;
import com.CityBoard.services.RequestsService;
import com.CityBoard.ui.operations.ClientOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUI implements ClientOperations {
    private final RequestsService requestsService;
    private final AdvertsService advertsService;

    public ClientUI(RequestsService requestsService, AdvertsService advertsService) {
        this.requestsService = requestsService;
        this.advertsService = advertsService;
    }

    @Override
    public Requests makeRequest(Users user, Long advertId, RequestType type) {
        Requests request = null;
        Adverts advert = advertsService.getAdvertById(advertId);
        if (advert != null) {
            request = requestsService.createRequest(user, advert, type);
        }
        return request;
    }

    @Override
    public List<Requests> getIncomingRequests(Users user) {
        return requestsService.getIncomingRequests(user);
    }

    @Override
    public List<Requests> getOutgoingRequests(Users user) {
        return requestsService.getOutgoingRequests(user);
    }

    @Override
    public void acceptRequest(Long requestId) {
        Requests request = requestsService.getRequestById(requestId);
        if (request != null) {
            requestsService.acceptRequest(request);
        }
    }

    @Override
    public void rejectRequest(Long requestId) {
        Requests request = requestsService.getRequestById(requestId);
        if (request != null) {
            requestsService.rejectRequest(request);
        }
    }

    @Override
    public List<Adverts> viewAvailableAdverts(Users user) {
        if (user != null) {
            return advertsService.getAvailableAdvertsNotAuthored(user);
        }
        return advertsService.getAvailableAdverts();
    }

    @Override
    public List<Adverts> viewAuthoredAdverts(Users user) {
        if (user != null) {
            return advertsService.getAuthoredAdverts(user);
        }
        return null;
    }

    @Override
    public Adverts viewAdvert(Long advertId) {
        return advertsService.getAdvertById(advertId);
    }

    @Override
    public Adverts createAdvert(Users user, AdvertDTO advertDTO) {
        Adverts advert = advertsService.createAdvert(user, advertDTO);
        if (advert != null) {
            advertsService.save(advert);
        }
        return advert;
    }

    @Override
    public Adverts updateAdvert(Long advertId, AdvertDTO advertDTO) {
        return advertsService.updateAdvert(advertId, advertDTO);
    }

    @Override
    public void hideAdvert(Long advertId) {
        Adverts advert = advertsService.getAdvertById(advertId);
        if (advert != null) {
            advertsService.hideAdvert(advert);
            advertsService.save(advert);
        }
    }

    @Override
    public void deleteAdvert(Long advertId) {
        Adverts advert = advertsService.getAdvertById(advertId);
        if (advert != null) {
            advertsService.deleteAdvert(advert);
            advertsService.save(advert);
        }
    }
}
