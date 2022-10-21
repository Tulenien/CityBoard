package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.CommonRepository;
import com.CityBoard.postgresql.dto.AdvertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertsRepository extends CommonRepository<AdvertDTO> {
    Page<AdvertDTO> findAll(Pageable pageable);

    @Query(value = "select * from adverts where status = 0", nativeQuery = true)
    Page<AdvertDTO> findAllVisiblePaginated(Pageable pageable);

    @Query(value = "select * from adverts where status <> 2",
           countQuery = "select count(*) from adverts where status <> 2", nativeQuery = true)
    Page<AdvertDTO> findAllNotDeletedPaginated(Pageable pageable);

    @Query(value = "select * from adverts where author_id <> ?1 and status = 0",
           countQuery = "select count(*) from adverts where author_id <> ?1 and status = 0", nativeQuery = true)
    Page<AdvertDTO> findVisibleNotAuthoredPaginated(Long authorId, Pageable pageable);

    @Query(value = "select * from adverts where author_id = ?1 and status <> 2", nativeQuery = true)
    List<AdvertDTO> findAllAuthored(Long authorId);

    @Override
    Optional<AdvertDTO> findById(Long id);
}
