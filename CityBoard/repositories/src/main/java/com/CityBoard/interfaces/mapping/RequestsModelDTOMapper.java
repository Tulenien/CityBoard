package com.CityBoard.interfaces.mapping;

import com.CityBoard.dto.RequestDTO;
import com.CityBoard.interfaces.dbmodels.RequestsModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RequestsModelDTOMapper {
    RequestDTO mapRequestsModelToDTO(RequestsModel requestsModel);

    List<RequestDTO> mapRequestsModelListToDTO(List<RequestsModel> requestsModelList);

    RequestsModel mapDTOtoRequestsModel(RequestDTO requestDTO);
}
