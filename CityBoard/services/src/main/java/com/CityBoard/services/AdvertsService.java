package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dto.AdvertDTO;
import com.CityBoard.postgresql.repository.AdvertsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdvertsService extends AbstractService<AdvertDTO, AdvertsRepository> {
    public AdvertsService(AdvertsRepository repository) {
        super(repository);
    }

    public AdvertDTO createAdvert(Adverts advert) {
        AdvertDTO dto = new AdvertDTO();
        dto.mapEntity(advert);
        dto.setStatus(AdvertStatus.VISIBLE);
        dto.setModCheck(false);
        return dto;
    }

    public AdvertDTO updateAdvert(Adverts advert) {
        AdvertDTO dto = repository.findById(advert.getId()).orElse(null);
        if (dto != null) {
            dto.mapEntity(advert);
            dto.setModCheck(false);
            return dto;
        }
        return null;
    }

    public Page<Adverts> getAllAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<AdvertDTO> dtoPage = repository.findAll(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Page<Adverts> getVisibleAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<AdvertDTO> dtoPage = repository.findAllVisiblePaginated(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Page<Adverts> getVisibleNotAuthoredAdvertsPage(Long authorId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<AdvertDTO> dtoPage = repository.findVisibleNotAuthoredPaginated(authorId, pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public List<Adverts> getAuthoredAdvertsList(Long authorId) {
        return mapDTOtoEntityList(repository.findAllAuthored(authorId));
    }

    public void changeAdvertStatus(Long advertId, AdvertStatus status) {
        AdvertDTO advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            advert.setStatus(status);
            save(advert);
        }
    }

    public void changeAdvertModCheck(Long advertId) {
        AdvertDTO advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            advert.setModCheck(!advert.isModCheck());
            save(advert);
        }
    }

    public Adverts getAdvertById(Long advertId) {
        AdvertDTO advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            return advert.mapDTOtoEntity();
        }
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

    @Override
    public void delete(AdvertDTO entity) {
        repository.delete(entity);
    }

    @Override
    public void save(AdvertDTO entity) {
        repository.save(entity);
    }
}
