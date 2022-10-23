package com.CityBoard.ui.operations;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Users;
import com.CityBoard.ui.pagination.Paged;

import java.util.List;

public interface CommonOperations {

    Paged<Adverts> getAvailableAdvertsPaged(Users user, int currentPage, int pageSize);

    Adverts getAdvert(Long advertId);
}
