package com.CityBoard.ui.operations;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.dto.AdvertDTO;
import com.CityBoard.models.enums.RequestType;

import java.util.List;

public interface ClientOperations {
    Requests makeRequest(Users user, Long advertId, RequestType type);

    List<Requests> getIncomingRequests(Users user);

    List<Requests> getOutgoingRequests(Users user);

    void acceptRequest(Long requestId);

    void rejectRequest(Long requestId);

    List<Adverts> viewAvailableAdverts(Users user);

    List<Adverts> viewAuthoredAdverts(Users user);
    Adverts viewAdvert(Long advertId);

    Adverts createAdvert(Users user, AdvertDTO advertDTO);

    Adverts updateAdvert(Long advertId, AdvertDTO advertDTO);

    void hideAdvert(Long advertId);

    void deleteAdvert(Long advertId);

    void concludeDeal(Long requestId);
}
