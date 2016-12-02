package com.bship.games;

import com.bship.games.domains.Game;
import com.bship.games.domains.Ship;
import com.bship.games.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

    @RequestMapping(
            value = "/boards/{boardId}",
            method = POST,
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ResponseEntity placeShip(@PathVariable Long boardId,
                                    @Valid @RequestBody Ship ship,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(BAD_REQUEST).body(getErrorBody(result));
        }
        return ResponseEntity.status(CREATED).body(service.placeShip(boardId, ship));
    }

    private String getErrorBody(BindingResult result) {
        return "{\"error\": " + result.getFieldErrors().stream().map(err ->
                "{\"field\": \"" + err.getField() + "\", " + "\"message\": \"" +
                        err.getRejectedValue() + " " + err.getDefaultMessage() + "\"}").collect(toList()) + "}";
    }
}
