package com.bship.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateGameController {

    private GameService service;

    @Autowired
    public CreateGameController(GameService service) {

        this.service = service;
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame() {
        return service.getNewGame();
    }

    @RequestMapping(value = "/games/{gameId}/boards/{boardId}/ship", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String placeShip(@PathVariable Integer gameId, @PathVariable Integer boardId, @RequestBody Ship battleShipToBeCreated) {
        service.placeShip(gameId, boardId, battleShipToBeCreated);
        return "{}";
    }
}
