package com.CityBoard.common.mapping.inner;

public interface DTOtoModelMapper<T, S>{
    S mapSingle(T dto);
}