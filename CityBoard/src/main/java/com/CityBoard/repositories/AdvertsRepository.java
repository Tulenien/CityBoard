package com.CityBoard.repositories;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.enums.AdvertStatus;
import com.CityBoard.models.enums.AdvertType;

import java.util.List;
import java.util.Optional;

public interface AdvertsRepository extends CommonRepository<Adverts> {
    @Override
    List<Adverts> findAll();
    @Override
    Optional<Adverts> findById(Long id);
    List<Adverts> findByType(AdvertType type);
    List<Adverts> findByStatus(AdvertStatus status);
    List<Adverts> findByModCheck(boolean mod_check);
}
