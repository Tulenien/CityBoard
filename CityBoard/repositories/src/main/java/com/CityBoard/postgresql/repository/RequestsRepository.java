package com.CityBoard.postgresql.repository;

import com.CityBoard.interfaces.CommonRepository;
import com.CityBoard.postgresql.dto.RequestDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestsRepository extends CommonRepository<RequestDTO> {
    @Override
    Optional<RequestDTO> findById(Long id);

    @Query(value = "select * from requests where requests.advert_id in (select adverts.id from adverts " +
            "where author_id = ?1) and requests.status = 0", nativeQuery = true)
    List<RequestDTO> findIncoming(Long userId);

    @Query(value = "select * from requests where requests.author_id = ?1 and status <> 3", nativeQuery = true)
    List<RequestDTO> findOutgoing(Long userId);
}
