package com.CityBoard.postgresql.mapping.inner;

import com.CityBoard.dto.RequestDTO;
import com.CityBoard.interfaces.dbmodels.RequestsModel;
import com.CityBoard.interfaces.mapping.RequestsModelDTOMapper;

import java.util.List;

public class RequestsModelDTOMapperImpl implements RequestsModelDTOMapper {
    @Override
    public RequestDTO mapRequestsModelToDTO(RequestsModel requestsModel) {
        return null;
    }

    @Override
    public List<RequestDTO> mapRequestsModelListToDTO(List<RequestsModel> requestsModelList) {
        return null;
    }

    @Override
    public RequestsModel mapDTOtoRequestsModel(RequestDTO requestDTO) {
        return null;
    }
}
