package com.bship.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateGameController {

    private GameService service;

    @Autowired
    public CreateGameController(GameService service) {

        this.service = service;
    }

    @RequestMapping(value = "/game",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame() {
        return service.getNewGame();
    }
}
