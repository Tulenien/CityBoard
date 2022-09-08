package com.CityBoard.controllers;

import com.CityBoard.models.AbstractEntity;
import com.CityBoard.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.MappedSuperclass;
@MappedSuperclass
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>> {
    protected final S service;

    @Autowired
    protected AbstractController(S service) {
        this.service = service;
    }
}
