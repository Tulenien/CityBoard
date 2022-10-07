package com.CityBoard.repositories;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdvertsRepository extends CommonRepository<Adverts> {
    @Override
    List<Adverts> findAll();

    @Query(value = "select * from adverts where status = 0", nativeQuery = true)
    List<Adverts> findAllVisible();

    @Override
    Optional<Adverts> findById(Long id);

    List<Adverts> findByType(AdvertType type);

    List<Adverts> findByStatus(AdvertStatus status);

    List<Adverts> findByModCheck(boolean mod_check);

    @Query(value = "select * from adverts where author_id in (select users.id from users where username = ?1) and status <> 2", nativeQuery = true)
    List<Adverts> findAllVisibleNotAuthored(String username);
}
