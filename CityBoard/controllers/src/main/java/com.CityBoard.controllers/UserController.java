package com.CityBoard.controllers;

import com.CityBoard.ui.ClientUI;
import com.CityBoard.ui.NoRegUI;

public class UserController {
    private final NoRegUI noRegUI;
    private final ClientUI clientUI;

    public UserController(NoRegUI noRegUI, ClientUI clientUI) {
        this.noRegUI = noRegUI;
        this.clientUI = clientUI;
    }
}
