package com.bship.games.logic

import com.bship.games.endpoints.board.errors.exceptions.ShipCollisionCheck
import com.bship.games.endpoints.board.errors.exceptions.ShipExistsCheck
import com.bship.games.endpoints.cabinet.entity.*
import com.bship.games.endpoints.game.errors.MoveCollision
import com.bship.games.endpoints.game.errors.TurnCheck
import com.bship.games.logic.definitions.MoveStatus
import com.bship.games.logic.definitions.MoveStatus.HIT
import com.bship.games.logic.definitions.MoveStatus.MISS
import com.bship.games.util.Util
import com.bship.games.util.Util.Companion.concat
import com.bship.games.util.Util.Companion.detectCollision
import org.springframework.stereotype.Service
import java.util.*
import java.util.Arrays.asList
import java.util.Collections.emptyList
import java.util.Objects.nonNull
import java.util.Optional.ofNullable
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Collectors.toList

@Service
class Battleship : GameLogic {

    @Throws(ShipExistsCheck::class, ShipCollisionCheck::class)
    override fun placementCheck(pieces: List<Piece>): Predicate<Board> {
        return Predicate { board ->
            if (exists(pieces, board)) throw ShipExistsCheck()
            if (collision(pieces, board)) throw ShipCollisionCheck()
            true
        }
    }

    @Throws(TurnCheck::class, MoveCollision::class)
    override fun valid(move: Move): Predicate<Game> {
        return Predicate { game ->
            if (wrongTurn(move, game)) throw TurnCheck()
            if (collision(move, game)) throw MoveCollision()
            true
        }
    }

    override fun play(move: Move): Function<Game, Optional<Game>>? {
        return Function { game ->
            ofNullable(game)
                    .map { it.boards }
                    .map(updateBoards(move))
                    .map(update(game))
        }
    }

    override fun setNextTurn(move: Move): Function<Game, Game> {
        return Function { game ->
            if (game.isOver) game.copy().withTurn(null).build()
            else {
                val other = partitionBoards(move).apply(game.boards)[OPPONENT_BOARD]
                game.copy().withTurn(other?.id).build()
            }
        }
    }

    private fun update(game: Game): Function<List<Board>, Game> {
        return Function { boards ->
            game.copy()
                    .withBoards(boards)
                    .withOver(boards.stream().anyMatch({ (_, _, _, _, _, _, isWinner) -> isWinner }))
                    .build()
        }
    }

    private fun updateBoards(move: Move): Function<List<Board>, List<Board>> {
        return Function { boards ->
            ofNullable(boards)
                    .map(partitionBoards(move))
                    .map { boardMap ->
                        val current = boardMap[CURRENT_BOARD]!!
                        val opponent = boardMap[OPPONENT_BOARD]!!

                        val status = getStatus(move, opponent.pieces)
                        val newMove = move.copy().withStatus(status).build()

                        val currentBuild = current.copy(moves = current.moves + newMove)
                        val opponentBuild = opponent.copy(opponentMoves = current.opponentMoves + newMove)

                        asList(
                                updateBoard(currentBuild, opponentBuild),
                                updateBoard(opponentBuild, currentBuild)
                        )
                    }.orElse(emptyList())
        }
    }

    private fun updateBoard(current: Board, opponent: Board): Board {
        val taken = getSunk(sinkShips(current, opponent))
        val ships = sinkShips(opponent, current)
        return ofNullable(current).map({ it ->
            it.copy(pieces = ships,
                    opponentPieces = taken,
                    winner = ships.size == taken.size)
        }).orElse(current)
    }

    private fun sinkShips(current: Board, opponent: Board): List<Piece> {
        return opponent.pieces.stream().map(sinkShip(current.moves)).collect(toList())
    }

    private fun getSunk(pieces: List<Piece>): List<Piece> {
        return pieces.filter { it.taken }
    }

    private fun sinkShip(moves: List<Move>): Function<Piece, Piece> {
        return Function { piece ->
            ofNullable(piece)
                    .filter(isSunk(moves))
                    .map { it.copy(taken = SUNK) }
                    .orElse(piece)
        }
    }

    private fun isSunk(moves: List<Move>): (Piece) -> Boolean {
        return { piece ->
            ofNullable(piece)
                    .map { Util.pointsRange(it) }
                    .filter { points -> moves.map { it.point }.containsAll(points) }
                    .isPresent
        }
    }

    private fun exists(pieces: List<Piece>, board: Board): Boolean {
        return ofNullable(pieces).isPresent && ofNullable(board)
                .map { it.pieces }
                .map { it.stream() }
                .filter { ships ->
                    ships.filter { Util.isPlaced(it) }
                            .map { it.type }
                            .anyMatch({ type ->
                                pieces.stream()
                                        .anyMatch { piece -> type === piece.type }
                            })
                }.isPresent
    }

    private fun collision(pieces: List<Piece>, board: Board): Boolean {
        return ofNullable(board)
                .map({ it.pieces })
                .filter { savedPieces -> detectCollision(concat(savedPieces, pieces)) }
                .isPresent
    }

    private fun getStatus(move: Move, pieces: List<Piece>): MoveStatus {
        return pieces.stream()
                .map<List<Point>> { Util.pointsRange(it) }
                .flatMap<Point> { it.stream() }
                .filter { point -> point == move.point }
                .findAny().map { _ -> HIT }.orElse(MISS)
    }

    private fun collision(move: Move, game: Game): Boolean {
        return ofNullable(game).map { it.boards }
                .map(partitionBoards(move))
                .map<Board> { boards -> boards[CURRENT_BOARD] }
                .filter { (_, _, _, _, moves) -> moves.contains(move) }
                .isPresent
    }

    private fun wrongTurn(move: Move, game: Game): Boolean {
        return nonNull(game.turn) && game.turn != move.boardId
    }

    private fun partitionBoards(move: Move): Function<List<Board>, Map<Boolean, Board>> {
        return Function { it.associateBy { (id) -> id == move.boardId } }
    }

    companion object {

        private val CURRENT_BOARD = true
        private val OPPONENT_BOARD = false
        private val SUNK = true
    }
}