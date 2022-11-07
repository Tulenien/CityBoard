package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dbmodels.AdvertsModel;
import com.CityBoard.postgresql.repository.AdvertsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdvertsService extends AbstractService<AdvertsModel, AdvertsRepository> {
    public AdvertsService(AdvertsRepository repository) {
        super(repository);
    }

    public AdvertsModel createAdvert(Adverts advert) {
        AdvertsModel dto = new AdvertsModel();
        dto.mapEntity(advert);
        dto.setStatus(AdvertStatus.VISIBLE);
        dto.setModCheck(false);
        return dto;
    }

    public AdvertsModel updateAdvert(Adverts advert) {
        AdvertsModel dto = repository.findById(advert.getId()).orElse(null);
        if (dto != null) {
            dto.mapEntity(advert);
            dto.setModCheck(false);
            return dto;
        }
        return null;
    }

    public Page<Adverts> getAllAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<AdvertsModel> dtoPage = repository.findAll(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Page<Adverts> getVisibleAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<AdvertsModel> dtoPage = repository.findAllVisiblePaginated(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Page<Adverts> getNotDeletedAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<AdvertsModel> dtoPage = repository.findAllNotDeletedPaginated(pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public Page<Adverts> getVisibleNotAuthoredAdvertsPage(Long authorId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<AdvertsModel> dtoPage = repository.findVisibleNotAuthoredPaginated(authorId, pageable);
        return mapDTOtoEntityPage(dtoPage, pageable);
    }

    public List<Adverts> getAuthoredAdvertsList(Long authorId) {
        return mapDTOtoEntityList(repository.findAllAuthored(authorId));
    }

    public boolean changeAdvertStatus(Long advertId, AdvertStatus status) {
        AdvertsModel advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            advert.setStatus(status);
            save(advert);
            return true;
        }
        return false;
    }

    public boolean changeAdvertModCheck(Long advertId) {
        AdvertsModel advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            advert.setModCheck(!advert.isModCheck());
            save(advert);
            return true;
        }
        return false;
    }

    public Adverts getAdvertById(Long advertId) {
        AdvertsModel advert = repository.findById(advertId).orElse(null);
        if (advert != null) {
            return advert.mapDTOtoEntity();
        }
        return null;
    }

    public AdvertsModel getAdvertDTOById(Long advertId) {
        return repository.findById(advertId).orElse(null);
    }

    private Page<Adverts> mapDTOtoEntityPage(Page<AdvertsModel> dtoPage, Pageable pageable) {
        List<AdvertsModel> dtoList = dtoPage.getContent();
        long totalElements = dtoPage.getTotalElements();

        List<Adverts> advertsList = new ArrayList<>();
        for (AdvertsModel dto : dtoList) {
            advertsList.add(dto.mapDTOtoEntity());
        }
        return new PageImpl<>(advertsList, pageable, totalElements);
    }

    private List<Adverts> mapDTOtoEntityList(List<AdvertsModel> dtoList) {
        List<Adverts> adverts = new ArrayList<>();
        for (AdvertsModel dto : dtoList) {
            adverts.add(dto.mapDTOtoEntity());
        }
        return adverts;
    }

    @Override
    public void delete(AdvertsModel entity) {
        repository.delete(entity);
    }

    @Override
    public void save(AdvertsModel entity) {
        repository.save(entity);
    }
}
