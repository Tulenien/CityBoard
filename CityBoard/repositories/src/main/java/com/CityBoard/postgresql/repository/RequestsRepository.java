package com.CityBoard.interfaces;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestsRepository extends CommonRepository<Requests> {
    @Override
    List<Requests> findAll();

    @Override
    Optional<Requests> findById(Long id);

    List<Requests> findByUser(Users user);

    //@Query(value = "select * from requests where requests.advert_id in (select adverts.id from adverts where author_id = ?1) and requests.status = 0", nativeQuery = true);
    @Query(value = "select * from requests where requests.advert_id in (select adverts.id from adverts where author_id = ?1) " +
            "and requests.status = 0", nativeQuery = true)
    List<Requests> findIncoming(Long userId);

    @Query(value = "select * from requests where requests.author_id = ?1 and status <> 3", nativeQuery = true)
    List<Requests> findOutgoing(Long userId);

    List<Requests> findByAdvert(Adverts advert);
}
