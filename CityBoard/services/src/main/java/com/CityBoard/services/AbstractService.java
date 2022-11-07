package com.CityBoard.services;

import com.CityBoard.postgresql.dbmodels.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService<E extends AbstractModel, R extends org.springframework.data.jpa.repository.JpaRepository<E, Long>> implements CommonService<E> {
    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }
}