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

    void acceptRequest(Long requestId);

    void rejectRequest(Long requestId);

    boolean createAdvert(Adverts advert);

    boolean updateAdvert(Adverts advert);

    void hideAdvert(Long advertId);

    void revealAdvert(Long advertId);

    void deleteAdvert(Long advertId);
}
