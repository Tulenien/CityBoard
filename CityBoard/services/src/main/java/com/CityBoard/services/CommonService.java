package com.CityBoard.services;

import com.CityBoard.models.AbstractEntity;

public interface CommonService<E extends AbstractEntity> {
    void save(E entity);

    void delete(E entity);
}
