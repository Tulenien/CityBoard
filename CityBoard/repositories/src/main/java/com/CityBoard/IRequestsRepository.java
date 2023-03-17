package com.CityBoard;

import com.CityBoard.models.Requests;
import com.CityBoard.models.enums.RequestStatus;
import com.CityBoard.models.enums.RequestType;

import java.util.List;

public interface IRequestsRepository {
    Requests findById(Long id);

    List<Requests> findIncoming(Long userId);

    List<Requests> findOutgoing(Long userId);

    boolean createRequest(Long authorId, Long advertId,
                          RequestType type);

    boolean updateRequestStatus(Long requestId, RequestStatus status);
}
