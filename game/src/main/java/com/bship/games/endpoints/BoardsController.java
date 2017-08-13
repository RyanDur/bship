package com.bship.games.endpoints;

import com.bship.games.domains.Board;
import com.bship.games.domains.PieceList;
import com.bship.games.exceptions.BoardValidation;
import com.bship.games.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.badRequest;

@RestController
public class BoardsController implements BadRequestHandler {

    private BoardService service;

    private Function<Exception, ObjectError> pieceError;

    @Autowired
    public BoardsController(BoardService service) {
        pieceError = error.apply("board");
        this.service = service;
    }

    @PutMapping(
            value = "/boards/{boardId}",
            produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(OK)
    public Board placeShip(@PathVariable Long boardId,
                           @RequestBody @Valid PieceList pieces) throws BoardValidation {
        return service.placePiece(boardId, pieces.getPieces());
    }

    @Override
    @ExceptionHandler({BoardValidation.class})
    public ResponseEntity processValidationError(Exception check) {
        return badRequest().body(getErrors(of(check).map(pieceError).map(Stream::of).map(objectErrors)));
    }
}
