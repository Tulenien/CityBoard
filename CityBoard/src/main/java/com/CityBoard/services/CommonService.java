package com.CityBoard.services;

import com.CityBoard.models.AbstractEntity;

import java.util.List;

public interface CommonService<E extends AbstractEntity> {
    void save(E entity);
    //void update(E entity);
    //void deleteById(Long id);
    //E findById(Long id);
    //List<E> findAll();
}
