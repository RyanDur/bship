package com.bship.games.endpoints.board

import com.bship.games.endpoints.BadRequestHandler
import com.bship.games.endpoints.board.errors.exceptions.BoardValidation
import com.bship.games.endpoints.board.errors.validations.bulk.PieceList
import com.bship.games.endpoints.cabinet.entity.Board
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.web.bind.annotation.*
import java.util.Optional.of
import javax.validation.Valid

@RestController
open class BoardsController(private val service: BoardService) : BadRequestHandler {

    private val pieceError = BadRequestHandler.error("board")

    @PutMapping(
            value = "/boards/{boardId}",
            produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    @ResponseStatus(OK)
    @Throws(BoardValidation::class)
    open fun placeShip(@PathVariable boardId: Long,
                       @RequestBody @Valid pieces: PieceList): Board {
        return service.placePiece(boardId, pieces.pieces)
    }

    @ExceptionHandler(BoardValidation::class)
    override fun processValidationError(check: Exception): ResponseEntity<*> {
        return badRequest().body(getErrors(
                of(check).map(pieceError)
                        .map { listOf(it) }
                        .map { BadRequestHandler.objectErrors }
        ))
    }
}
