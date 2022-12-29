package com.CityBoard.interfaces;

import com.CityBoard.postgresql.dbmodels.RequestsModel;

import java.util.List;

public interface RequestsRepository {
    RequestsModel findRequestById(Long id);

    List<RequestsModel> findIncomingRequestsListByAuthor(Long authorId);

    List<RequestsModel> findOutgoingRequestsListByAuthor(Long authorId);

    void persist(RequestsModel model);

    void update(RequestsModel model);
}
