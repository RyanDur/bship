package com.bship.games.endpoints;

import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class GameController implements BadRequestHandler {

    private GameService service;
    private Function<Exception, ObjectError> gameError;

    public GameController() {
        gameError = error.apply("game");
    }

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

    @RequestMapping(
            value = "/games/{gameId}/boards/{boardId}",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Move placeMove(@PathVariable Long gameId,
                          @PathVariable Long boardId,
                          @Valid @RequestBody Point point) {
        return service.placeMove(gameId, boardId, point);
    }

    @Override
    public ResponseEntity processValidationError(Exception check) {
        return null;
    }
}
