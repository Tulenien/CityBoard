package com.CityBoard.common.repository;

import com.CityBoard.dto.AdvertDTO;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.postgresql.dto.AdvertRepositoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertsRepository {
    Page<AdvertRepositoryDTO> findAdvertsPageNoFilter(Pageable pageable);

    Page<AdvertDTO> findAdvertsPageWithStatus(Pageable pageable, AdvertStatus status);

    Page<AdvertDTO> findAdvertsPageWithoutStatus(Pageable pageable, AdvertStatus status);

    Page<AdvertDTO> findAdvertsPageByNotAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId);

    Page<AdvertDTO> findAdvertsPageByAuthorWithoutStatus(Pageable pageable, AdvertStatus status, Long authorId);

    Page<AdvertDTO> findAdvertsPageByAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId);

    List<AdvertDTO> findAdvertsListByAuthor(Long authorId);

    AdvertDTO findAdvertById(Long advertId);

    void persist(AdvertDTO model);

    void update(AdvertDTO model);
}
