package com.CityBoard.interfaces;

import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dbmodels.AdvertsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertsRepository {
    Page<AdvertsModel> findAdvertsPageWithStatus(Pageable pageable, AdvertStatus status);
    Page<AdvertsModel> findAdvertsPageWithoutStatus(Pageable pageable, AdvertStatus status);
    Page<AdvertsModel> findAdvertsPageByNotAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId);
    Page<AdvertsModel> findAdvertsPageByAuthorWithoutStatus(Pageable pageable, AdvertStatus status, Long authorId);
    Page<AdvertsModel> findAdvertsPageByAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId);
    List<AdvertsModel> findAdvertsListByAuthor(Pageable pageable, Long authorId);
    AdvertsModel findAdvertById(Long advertId);
}
