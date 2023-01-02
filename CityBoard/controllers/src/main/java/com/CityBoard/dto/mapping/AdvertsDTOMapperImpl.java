package com.CityBoard.dto.mapping;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.models.Adverts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

import java.util.List;

@Qualifier("controller")
public class AdvertsDTOMapperImpl implements AdvertsDTOMapper {
    @Override
    public AdvertDTO mapAdvertsToDTO(Adverts advert) {
        return null;
    }

    @Override
    public List<AdvertDTO> mapAdvertsListToDTO(List<Adverts> adverts) {
        return null;
    }

    @Override
    public Page<AdvertDTO> mapAdvertsPageToDTO(Page<Adverts> adverts) {
        return null;
    }

    @Override
    public Adverts mapDTOtoAdverts(AdvertDTO dto) {
        return null;
    }

    @Override
    public List<Adverts> mapDTOtoAdvertsList(List<AdvertDTO> dto) {
        return null;
    }

    @Override
    public Page<Adverts> mapDTOtoAdvertsPage(Page<AdvertDTO> dto) {
        return null;
    }
}
