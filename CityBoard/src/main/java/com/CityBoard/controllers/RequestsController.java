package com.CityBoard.controllers;

import com.CityBoard.models.Requests;
import com.CityBoard.services.RequestsService;

public class RequestsController extends AbstractController<Requests, RequestsService> {

    protected RequestsController(RequestsService service) {
        super(service);
    }
}
