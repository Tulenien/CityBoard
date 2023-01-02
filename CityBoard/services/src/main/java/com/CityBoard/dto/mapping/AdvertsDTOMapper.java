package com.CityBoard.dto.mapping;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.models.Adverts;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AdvertsDTOMapper {
    AdvertDTO mapAdvertsToDTO(Adverts advert);

    List<AdvertDTO> mapAdvertsListToDTO(List<Adverts> adverts);

    Page<AdvertDTO> mapAdvertsPageToDTO(Page<Adverts> adverts);

    Adverts mapDTOtoAdverts(AdvertDTO dto);

    List<Adverts> mapDTOtoAdvertsList(List<AdvertDTO> dto);

    Page<Adverts> mapDTOtoAdvertsPage(Page<AdvertDTO> dto);
}
