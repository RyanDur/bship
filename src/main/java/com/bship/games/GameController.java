package com.bship.games;

import com.bship.games.models.Game;
import com.bship.games.models.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame() {
        return service.getNewGame();
    }

    @RequestMapping(value = "/games/{gameId}/boards/{boardId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String placeShip(@PathVariable Integer gameId, @PathVariable Integer boardId, @RequestBody Ship battleShipToBeCreated) {
        service.placeShip(gameId, boardId, battleShipToBeCreated);
        return "{}";
    }
}
