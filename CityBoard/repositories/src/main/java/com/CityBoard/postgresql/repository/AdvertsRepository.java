package com.CityBoard.postgresql.repository;

import com.CityBoard.postgresql.dbmodels.AdvertsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertsRepository extends org.springframework.data.jpa.repository.JpaRepository<AdvertsModel, Long> {
    Page<AdvertsModel> findAll(Pageable pageable);

    @Query(value = "select * from adverts where status = 0", nativeQuery = true)
    Page<AdvertsModel> findAllVisiblePaginated(Pageable pageable);

    @Query(value = "select * from adverts where status <> 2",
           countQuery = "select count(*) from adverts where status <> 2", nativeQuery = true)
    Page<AdvertsModel> findAllNotDeletedPaginated(Pageable pageable);

    @Query(value = "select * from adverts where author_id <> ?1 and status = 0",
           countQuery = "select count(*) from adverts where author_id <> ?1 and status = 0", nativeQuery = true)
    Page<AdvertsModel> findVisibleNotAuthoredPaginated(Long authorId, Pageable pageable);

    @Query(value = "select * from adverts where author_id = ?1 and status <> 2", nativeQuery = true)
    List<AdvertsModel> findAllAuthored(Long authorId);

    @Override
    Optional<AdvertsModel> findById(Long id);
}
