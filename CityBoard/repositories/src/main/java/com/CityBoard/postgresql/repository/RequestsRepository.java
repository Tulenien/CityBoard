package com.CityBoard.postgresql.repository;

import com.CityBoard.postgresql.dbmodels.RequestsModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestsRepository extends org.springframework.data.jpa.repository.JpaRepository<RequestsModel, Long> {
    @Override
    Optional<RequestsModel> findById(Long id);

    @Query(value = "select * from requests where requests.advert_id in (select adverts.id from adverts " +
            "where author_id = ?1) and requests.status = 0", nativeQuery = true)
    List<RequestsModel> findIncoming(Long userId);

    @Query(value = "select * from requests where requests.author_id = ?1 and status <> 3", nativeQuery = true)
    List<RequestsModel> findOutgoing(Long userId);
}
