package com.bship.games.endpoints;

import com.bship.games.domains.Game;
import com.bship.games.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class GameController {

    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @RequestMapping(
            value = "/games",
            method = POST,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Game createGame() {
        return service.getNewGame();
    }
}
