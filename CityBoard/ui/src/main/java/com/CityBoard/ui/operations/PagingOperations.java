package com.CityBoard.ui.operations;

public interface PagingOperations {

    void addPageItems(int from, int to, int pageNumber);

    void last(int pageSize);

    void first(int pageNumber);
}
