package com.CityBoard.interfaces.mapping;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.interfaces.dbmodels.AdvertsModel;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AdvertsModelDTOMapper {
    AdvertDTO mapAdvertsModelToDTO(AdvertsModel advertsModel);

    List<AdvertDTO> mapAdvertsModelListToDTO(List<AdvertsModel> advertsModelList);

    Page<AdvertDTO> mapAdvertsModelPageToDTO(Page<AdvertsModel> advertsModelPage);

    AdvertsModel mapDTOtoAdvertsModel(AdvertDTO advertDTO);
}
