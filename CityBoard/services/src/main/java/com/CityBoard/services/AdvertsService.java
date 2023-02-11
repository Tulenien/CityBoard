package com.CityBoard.services;

import com.CityBoard.tto.AdvertDTO;
import com.CityBoard.tto.mapping.AdvertsDTOMapper;
import com.CityBoard.interfaces.AdvertsRepository;
import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdvertsService {
    private final AdvertsRepository repository;
    private final AdvertsDTOMapper repositoryMapper;
    private final AdvertsDTOMapper controllerMapper;

    public AdvertsService(AdvertsRepository repository,
                          @Qualifier("repository") AdvertsDTOMapper repositoryMapper,
                          @Qualifier("controller") AdvertsDTOMapper controllerMapper) {
        this.repository = repository;
        this.repositoryMapper = repositoryMapper;
        this.controllerMapper = controllerMapper;
    }

    public AdvertDTO getAdvertDTOById(Long advertId) {
        Adverts advert = getAdvertsById(advertId);
        if (advert != null) {
            return controllerMapper.mapAdvertsToDTO(advert);
        }
        return null;
    }

    private Adverts getAdvertsById(Long advertId) {
        return repositoryMapper.mapDTOtoAdverts(repository.findAdvertById(advertId));
    }

    public void createAdvert(AdvertDTO advertDTO) {
        Adverts advert = controllerMapper.mapDTOtoAdverts(advertDTO);
        if (advert != null) {
            advert.setStatus(AdvertStatus.VISIBLE);
            advert.setModCheck(false);
            repository.persist(repositoryMapper.mapAdvertsToDTO(advert));
        }
    }

    public void updateAdvert(AdvertDTO advertDTO) {
        Adverts advert = controllerMapper.mapDTOtoAdverts(advertDTO);
        if (advert != null) {
            advert.setModCheck(false);
            repository.update(repositoryMapper.mapAdvertsToDTO(advert));
        }
    }

    public void hideAdvert(Long advertId) {
        Adverts advert = getAdvertsById(advertId);
        if (advert != null)
        {
            advert.setStatus(AdvertStatus.HIDDEN);
            repository.update(repositoryMapper.mapAdvertsToDTO(advert));
        }
    }

    public void revealAdvert(Long advertId) {
        Adverts advert = getAdvertsById(advertId);
        if (advert != null)
        {
            advert.setStatus(AdvertStatus.VISIBLE);
            repository.update(repositoryMapper.mapAdvertsToDTO(advert));
        }
    }

    public void deleteAdvert(Long advertId) {
        Adverts advert = getAdvertsById(advertId);
        if (advert != null)
        {
            advert.setStatus(AdvertStatus.DELETED);
            repository.update(repositoryMapper.mapAdvertsToDTO(advert));
        }
    }

    public void toggleModCheck(Long advertId) {
        Adverts advert = repositoryMapper.mapDTOtoAdverts(repository.findAdvertById(advertId));
        if (advert != null) {
            advert.setModCheck(!advert.isModCheck());
            repository.update(repositoryMapper.mapAdvertsToDTO(advert));
        }
    }

    public Page<AdvertDTO> getAllAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Adverts> advertsPage = repositoryMapper.mapDTOtoAdvertsPage(
                repository.findAdvertsPageNoFilter(pageable));
        return controllerMapper.mapAdvertsPageToDTO(advertsPage);
    }

    public Page<AdvertDTO> getVisibleAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Adverts> advertsPage = repositoryMapper.mapDTOtoAdvertsPage(
                repository.findAdvertsPageWithStatus(pageable, AdvertStatus.VISIBLE));
        return controllerMapper.mapAdvertsPageToDTO(advertsPage);
    }

    public Page<AdvertDTO> getNotDeletedAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Adverts> advertsPage = repositoryMapper.mapDTOtoAdvertsPage(
                repository.findAdvertsPageWithoutStatus(pageable, AdvertStatus.DELETED));
        return controllerMapper.mapAdvertsPageToDTO(advertsPage);
    }

    public Page<AdvertDTO> getVisibleNotAuthoredAdvertsPage(Long authorId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Adverts> advertsPage = repositoryMapper.mapDTOtoAdvertsPage(
                repository.findAdvertsPageByNotAuthorWithStatus(pageable, AdvertStatus.VISIBLE, authorId));
        return controllerMapper.mapAdvertsPageToDTO(advertsPage);
    }

    public List<AdvertDTO> getAuthoredAdvertsList(Long authorId) {
        List<Adverts> advertsList = repositoryMapper.mapDTOtoAdvertsList(repository.findAdvertsListByAuthor(authorId));
        return controllerMapper.mapAdvertsListToDTO(advertsList);
    }

    //private Page<Adverts> mapDTOtoEntityPage(Page<AdvertsModelImpl> dtoPage, Pageable pageable) {
    //    List<AdvertsModelImpl> dtoList = dtoPage.getContent();
    //    long totalElements = dtoPage.getTotalElements();
//
    //    List<Adverts> advertsList = new ArrayList<>();
    //    for (AdvertsModelImpl dto : dtoList) {
    //        advertsList.add(dto.mapDTOtoEntity());
    //    }
    //    return new PageImpl<>(advertsList, pageable, totalElements);
    //}
//
    //private List<Adverts> mapDTOtoEntityList(List<AdvertsModelImpl> dtoList) {
    //    List<Adverts> adverts = new ArrayList<>();
    //    for (AdvertsModelImpl dto : dtoList) {
    //        adverts.add(dto.mapDTOtoEntity());
    //    }
    //    return adverts;
    //}
}
