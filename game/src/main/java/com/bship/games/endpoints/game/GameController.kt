package com.bship.games.endpoints.game

import com.bship.games.endpoints.BadRequestHandler
import com.bship.games.endpoints.cabinet.entity.Game
import com.bship.games.endpoints.cabinet.entity.Move
import com.bship.games.endpoints.game.errors.GameValidation
import com.bship.games.logic.definitions.GameRules
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.web.bind.annotation.*
import java.util.Optional.of
import javax.validation.Valid

@RestController
class GameController @Autowired
constructor(private val service: GameService) : BadRequestHandler {
    private val gameError = BadRequestHandler.Companion.error("game")

    val games: Array<GameRules>
        @GetMapping("/games")
        get() = GameRules.values()

    @PostMapping(value = "/games/{game}", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    @ResponseStatus(CREATED)
    fun createGame(@PathVariable game: GameRules): Game {
        return service.getNewGame(game)
    }

    @PutMapping(value = "/games/{gameId}", produces = arrayOf(APPLICATION_JSON_UTF8_VALUE))
    @ResponseStatus(OK)
    @Throws(GameValidation::class)
    fun placeMove(@PathVariable gameId: Long?,
                  @Valid @RequestBody move: Move): Game {
        return service.placeMove(gameId, move)
    }

    @ExceptionHandler(GameValidation::class)
    override fun processValidationError(check: Exception): ResponseEntity<*> {
        return badRequest().body(getErrors(of(check).map(gameError)
                .map { listOf(it) }
                .map(BadRequestHandler.objectErrors)))
    }
}
