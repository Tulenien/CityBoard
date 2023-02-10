package com.CityBoard.interfaces.mapping;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.interfaces.dbmodels.AdvertsModel;
import com.CityBoard.postgresql.dbmodels.AbstractModel;
import com.CityBoard.postgresql.dbmodels.AdvertsModelImpl;
import com.CityBoard.postgresql.dto.AdvertRepositoryDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AdvertsModelDTOMapper {
    AdvertDTO mapAdvertsModelToDTO(AdvertsModelImpl advertsModel);

    List<AdvertDTO> mapAdvertsModelListToDTO(List<AdvertsModelImpl> advertsModelList);

    Page<AdvertRepositoryDTO> mapAdvertsModelPageToDTO(Page<? extends AbstractModel> advertsModelPage);

    AbstractModel mapDTOtoAdvertsModel(AdvertDTO advertDTO);
}
