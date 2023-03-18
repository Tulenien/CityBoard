package com.CityBoard.services;

import com.CityBoard.AdvertsRepository;
import com.CityBoard.IAdvertsRepository;
import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dto.AdvertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdvertsService {
    private final IAdvertsRepository advertsRepository;

    public AdvertsService(AdvertsRepository advertsRepository) {
        this.advertsRepository = advertsRepository;
    }

    public void createAdvert(Adverts advert, Long authorId) {
        advertsRepository.saveAdvert(advert, authorId);
    }

    public AdvertDTO updateAdvert(Adverts advert) {
        advertsRepository.updateAdvert(advert);
        return null;
    }

    public Page<Adverts> getAllAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return advertsRepository.findAll(pageable);
    }

    public Page<Adverts> getVisibleAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return advertsRepository.findAllVisiblePaginated(pageable);
    }

    public Page<Adverts> getNotDeletedAdvertsPage(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return advertsRepository.findAllNotDeletedPaginated(pageable);
    }


    public Page<Adverts> getVisibleNotAuthoredAdvertsPage(Long authorId, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return advertsRepository.findVisibleNotAuthoredPaginated(authorId, pageable);
    }

    public List<Adverts> getAuthoredAdvertsList(Long authorId) {
        return advertsRepository.findAllAuthored(authorId);
    }

    public boolean changeAdvertStatus(Long advertId, AdvertStatus status) {
        return advertsRepository.changeStatus(advertId, status);
    }

    public boolean changeAdvertModCheck(Long advertId) {
        return advertsRepository.changeModCheck(advertId);
    }

    public Adverts getAdvertById(Long advertId) {
        return advertsRepository.findById(advertId);
    }
}
