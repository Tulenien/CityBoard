package com.CityBoard.postgresql;

import com.CityBoard.models.Adverts;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.postgresql.repository.AdvertsJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class AdvertsRepository implements IAdvertsRepository {
    private final AdvertsJPARepository advertsJPARepository;

    public AdvertsRepository(AdvertsJPARepository advertsJPARepository) {
        this.advertsJPARepository = advertsJPARepository;
    }

    @Override
    public Page<Adverts> findAll(Pageable pageable) {
        Page<AdvertDTO> advertDTOPage = advertsJPARepository.findAll(pageable);
        return mapDTOtoEntityPage(advertDTOPage, pageable);
    }

    @Override
    public Page<Adverts> findAllVisiblePaginated(Pageable pageable) {
        Page<AdvertDTO> advertDTOPage = advertsJPARepository.findAllVisiblePaginated(pageable);
        return mapDTOtoEntityPage(advertDTOPage, pageable);
    }

    @Override
    public Page<Adverts> findAllNotDeletedPaginated(Pageable pageable) {
        Page<AdvertDTO> advertDTOPage = advertsJPARepository.findAllNotDeletedPaginated(pageable);
        return mapDTOtoEntityPage(advertDTOPage, pageable);
    }

    @Override
    public Page<Adverts> findVisibleNotAuthoredPaginated(Long authorId, Pageable pageable) {
        Page<AdvertDTO> advertDTOPage = advertsJPARepository.findVisibleNotAuthoredPaginated(authorId, pageable);
        return mapDTOtoEntityPage(advertDTOPage, pageable);
    }

    @Override
    public List<Adverts> findAllAuthored(Long authorId) {
        return null;
    }

    @Override
    public Adverts findById(Long id) {
        return null;
    }

    private Page<Adverts> mapDTOtoEntityPage(Page<AdvertDTO> dtoPage, Pageable pageable) {
        List<AdvertDTO> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Adverts> advertsList = new ArrayList<>();
        for (AdvertDTO dto : dtoList) {
            advertsList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(advertsList, pageable, totalElements);
    }
    private List<Adverts> mapDTOtoEntityList(List<AdvertDTO> dtoList) {
        List<Adverts> adverts = new ArrayList<>();
        for (AdvertDTO dto : dtoList) {
            adverts.add(dto.mapDTOtoEntity());
        }
        return adverts;
    }
}
