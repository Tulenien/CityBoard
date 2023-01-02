package com.CityBoard.common.repository;

import com.CityBoard.dto.RequestDTO;

import java.util.List;

public interface RequestsRepository {
    RequestDTO findRequestById(Long id);

    List<RequestDTO> findIncomingRequestsListByAuthor(Long authorId);

    List<RequestDTO> findOutgoingRequestsListByAuthor(Long authorId);

    void persist(RequestDTO model);

    void update(RequestDTO model);
}
