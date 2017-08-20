package com.bship.games.endpoints.board

import com.bship.games.endpoints.board.errors.exceptions.BoardExistence
import com.bship.games.endpoints.board.errors.exceptions.BoardValidation
import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.repositories.BoardRepository
import com.bship.games.logic.GameLogic
import org.springframework.stereotype.Service

@Service
open class BoardService(private val boards: BoardRepository, private val logic: GameLogic) {

    @Throws(BoardValidation::class)
    open fun placePiece(boardId: Long, pieces: List<Piece>): Board {
        return boards[boardId]
                .filter(logic.placementCheck(pieces))
                .map(addPiecesToBoard(pieces))
                .flatMap { boards.save(it) }
                .orElseThrow { BoardExistence() }
    }

    private fun addPiecesToBoard(pieces: List<Piece>): (Board) -> Board {
        return { it.copy(pieces = it.pieces + pieces) }
    }
}
