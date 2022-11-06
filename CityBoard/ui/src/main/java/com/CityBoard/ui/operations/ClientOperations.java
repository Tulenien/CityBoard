package com.CityBoard.ui.operations;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.models.enums.RequestType;
import com.CityBoard.postgresql.dto.UserDTO;

import java.util.List;

public interface ClientOperations {
    boolean makeRequest(UserDTO user, Long advertId, RequestType type);

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
