package com.CityBoard.dto.mapping;

import com.CityBoard.dto.RequestDTO;
import com.CityBoard.models.Requests;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Qualifier("controller")
public class RequestsDTOMapperImpl implements RequestsDTOMapper {
    @Override
    public RequestDTO mapRequestsToDTO(Requests request) {
        return null;
    }

    @Override
    public List<RequestDTO> mapRequestsListToDTO(List<Requests> requests) {
        return null;
    }

    @Override
    public Requests mapDTOtoRequests(RequestDTO dto) {
        return null;
    }

    @Override
    public List<Requests> mapDTOtoRequestsList(List<RequestDTO> dto) {
        return null;
    }
}
