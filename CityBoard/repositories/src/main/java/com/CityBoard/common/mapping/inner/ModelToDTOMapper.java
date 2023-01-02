package com.CityBoard.common.mapping.inner;

import com.CityBoard.common.dto.AdvertsRepositoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ModelToDTOMapper<T, S>{
    T mapSingle(S model);
    List<T> mapList(List<S> modelList);
    Page<T> mapPage(Page<S> modelPage);
}
