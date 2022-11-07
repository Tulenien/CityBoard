package com.CityBoard.interfaces;

import com.CityBoard.postgresql.dbmodels.RequestsModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestsRepository {
    RequestsModel findRequestById(Long id);
    List<RequestsModel> findIncomingRequestsListByAuthor(Long authorId);
    List<RequestsModel> findOutgoingRequestsListByAuthor(Long authorId);
}
