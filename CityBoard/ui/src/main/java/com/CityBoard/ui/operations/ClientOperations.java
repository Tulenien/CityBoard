package com.CityBoard.ui.operations;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dbmodels.UsersModel;

import java.util.List;

public interface ClientOperations {
    boolean makeRequest(UsersModel user, Long advertId, RequestType type);

    List<Requests> getIncomingRequests(Long userId);

    List<Requests> getOutgoingRequests(Long userId);

    boolean acceptRequest(Long requestId);

    boolean rejectRequest(Long requestId);

    boolean createAdvert(Adverts advert);

    boolean updateAdvert(Adverts advert);

    boolean hideAdvert(Long advertId);

    boolean revealAdvert(Long advertId);

    boolean deleteAdvert(Long advertId);

    List<Adverts> getUserAdverts(Long userId);
}
