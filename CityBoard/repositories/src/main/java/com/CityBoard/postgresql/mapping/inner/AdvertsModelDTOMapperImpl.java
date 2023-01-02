package com.CityBoard.postgresql.mapping.inner;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.interfaces.dbmodels.AdvertsModel;
import com.CityBoard.interfaces.mapping.AdvertsModelDTOMapper;
import com.CityBoard.postgresql.dbmodels.AdvertsModelImpl;
import org.springframework.data.domain.Page;

import java.util.List;

public class AdvertsModelDTOMapperImpl implements AdvertsModelDTOMapper {

    @Override
    public AdvertDTO mapAdvertsModelToDTO(AdvertsModel advertsModel) {
        return null;
    }

    @Override
    public List<AdvertDTO> mapAdvertsModelListToDTO(List<AdvertsModel> advertsModelList) {
        return null;
    }

    @Override
    public Page<AdvertDTO> mapAdvertsModelPageToDTO(Page<AdvertsModel> advertsModelPage) {
        return null;
    }

    @Override
    public AdvertsModelImpl mapDTOtoAdvertsModel(AdvertDTO advertDTO) {
        return null;
    }
}
