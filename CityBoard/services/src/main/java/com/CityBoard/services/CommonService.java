package com.CityBoard.services;


import com.CityBoard.interfaces.AbstractEntityDTO;

public interface CommonService<E extends AbstractEntityDTO> {
    void save(E entity);

    void delete(E entity);
}