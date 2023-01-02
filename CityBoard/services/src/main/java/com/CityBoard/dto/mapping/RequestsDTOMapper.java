package com.CityBoard.dto.mapping;

import com.CityBoard.dto.RequestDTO;
import com.CityBoard.models.Requests;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RequestsDTOMapper {
    RequestDTO mapRequestsToDTO(Requests request);

    List<RequestDTO> mapRequestsListToDTO(List<Requests> requests);

    Requests mapDTOtoRequests(RequestDTO dto);

    List<Requests> mapDTOtoRequestsList(List<RequestDTO> dto);
}
