package com.CityBoard.interfaces;

import com.CityBoard.entities.Adverts;
import com.CityBoard.repository.AdvertsRepositoryDTO;
import com.CityBoard.repository.enums.AdvertStatusRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertsRepository {
    Page<AdvertsRepositoryDTO> findAdvertsPageNoFilter(Pageable pageable);

    Page<AdvertsRepositoryDTO> findAdvertsPageWithStatus(Pageable pageable, Integer status);

    //Page<AdvertsRepositoryDTO> findAdvertsPageWithoutStatus(Pageable pageable, Integer status);

    //Page<AdvertsRepositoryDTO> findAdvertsPageByNotAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId);

    //Page<AdvertsRepositoryDTO> findAdvertsPageByAuthorWithoutStatus(Pageable pageable, AdvertStatus status, Long authorId);

    //Page<AdvertsRepositoryDTO> findAdvertsPageByAuthorWithStatus(Pageable pageable, AdvertStatus status, Long authorId);

    //List<AdvertsRepositoryDTO> findAdvertsListByAuthor(Long authorId);

    AdvertsRepositoryDTO findAdvertById(Long advertId);

    void persist(AdvertsRepositoryDTO dto);

    void update(AdvertsRepositoryDTO dto);
}
