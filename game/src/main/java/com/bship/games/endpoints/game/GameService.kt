package com.bship.games.endpoints.game

import com.bship.games.endpoints.cabinet.entity.Game
import com.bship.games.endpoints.cabinet.entity.Move
import com.bship.games.endpoints.cabinet.repositories.GameRepository
import com.bship.games.endpoints.game.errors.GameValidation
import com.bship.games.endpoints.game.errors.InvalidGame
import com.bship.games.logic.GameLogic
import com.bship.games.logic.definitions.GameRules
import org.springframework.stereotype.Service

@Service
open class GameService constructor(private val repository: GameRepository, private val logic: GameLogic) {

    open fun getNewGame(game: GameRules): Game {
        return repository.create(game)
    }

    @Throws(GameValidation::class)
    open fun placeMove(gameId: Long?, move: Move): Game {
        return repository.get(gameId)
                .filter(logic.valid(move))
                .flatMap(logic.play(move))
                .map(logic.setNextTurn(move))
                .flatMap(repository::save)
                .also { it.ifPresent { if (it.isOver) repository.delete(it) } }
                .orElseThrow<InvalidGame>({ InvalidGame() })
    }
}