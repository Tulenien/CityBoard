package com.CityBoard.controllers;

import com.CityBoard.models.AbstractEntity;

import java.security.Principal;


public interface CommonController<E extends AbstractEntity> {
    String create(Principal principal, E entity);
    //String update(E entity);
    //String findBy();
}
