package com.CityBoard.repositories;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;

import java.util.List;
import java.util.Optional;

public interface RequestsRepository extends CommonRepository<Requests> {
    @Override
    List<Requests> findAll();
    @Override
    Optional<Requests> findById(Long id);

    List<Requests> findByUser(Users user);
    List<Requests> findByAdvert(Adverts advert);
}
