package com.CityBoard.services;

import com.CityBoard.interfaces.AbstractEntityDTO;
import com.CityBoard.interfaces.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService<E extends AbstractEntityDTO, R extends CommonRepository<E>> implements CommonService<E> {
    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }
}