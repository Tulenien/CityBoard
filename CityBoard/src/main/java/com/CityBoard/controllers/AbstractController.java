package com.CityBoard.controllers;


import com.CityBoard.models.AbstractEntity;
import com.CityBoard.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.MappedSuperclass;
import java.security.Principal;
import java.util.List;
@MappedSuperclass
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>>
implements CommonController<E> {
    protected final S service;

    @Autowired
    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    public String create(Principal principal, E entity) {
        service.save(entity);
        return "redirect:/";
    }
}
