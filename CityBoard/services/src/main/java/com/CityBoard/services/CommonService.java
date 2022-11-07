package com.CityBoard.services;


import com.CityBoard.postgresql.dbmodels.AbstractModel;

public interface CommonService<E extends AbstractModel> {
    void save(E entity);

    void delete(E entity);
}