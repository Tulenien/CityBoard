package com.CityBoard;

import com.CityBoard.models.Requests;

import java.util.List;

public class RequestsRepository implements IRequestsRepository {
    @Override
    public Requests findById(Long id) {
        return null;
    }

    @Override
    public List<Requests> findIncoming(Long userId) {
        return null;
    }

    @Override
    public List<Requests> findOutgoing(Long userId) {
        return null;
    }
}
