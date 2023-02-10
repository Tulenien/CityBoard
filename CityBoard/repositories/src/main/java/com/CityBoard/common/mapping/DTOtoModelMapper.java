package com.CityBoard.common.mapping;

public interface DTOtoModelMapper<T, S>{
    S mapSingle(T dto);
}