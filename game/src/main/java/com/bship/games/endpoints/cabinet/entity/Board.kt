package com.bship.games.endpoints.cabinet.entity

import com.bship.games.Configuration.BoardDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.*
import java.util.Collections.emptyList

@JsonDeserialize(using = BoardDeserializer::class)
data class Board private constructor(val id: Long,
                                     val gameId: Long,
                                     val pieces: List<Piece>,
                                     val opponentPieces: List<Piece>,
                                     val moves: List<Move>,
                                     val opponentMoves: List<Move>,
                                     val winner: Boolean) {

    private constructor(builder: Builder) : this(
            id = builder.id.orElse(0),
            gameId = builder.gameId.orElse(0),
            pieces = builder.pieces.orElse(emptyList()),
            opponentPieces = builder.opponentPieces.orElse(emptyList()),
            moves = builder.moves.orElse(emptyList()),
            opponentMoves = builder.opponentMoves.orElse(emptyList()),
            winner = builder.winner.orElse(false)
    )

    class Builder {
        internal var id: Optional<Long> = Optional.empty(); private set
        internal var gameId: Optional<Long> = Optional.empty(); private set
        internal var winner: Optional<Boolean> = Optional.empty(); private set
        internal var moves: Optional<List<Move>> = Optional.empty(); private set
        internal var pieces: Optional<List<Piece>> = Optional.empty(); private set
        internal var opponentMoves: Optional<List<Move>> = Optional.empty(); private set
        internal var opponentPieces: Optional<List<Piece>> = Optional.empty(); private set

        fun withId(init: Builder.() -> Long?) = apply {
            id = Optional.ofNullable(init())
        }

        fun withGameId(init: Builder.() -> Long?) = apply {
            gameId = Optional.ofNullable(init())
        }

        fun withPieces(init: Builder.() -> List<Piece>?) = apply {
            pieces = Optional.ofNullable(init())
        }

        fun withOpponentPieces(init: Builder.() -> List<Piece>?) = apply {
            opponentPieces = Optional.ofNullable(init())
        }

        fun withMoves(init: Builder.() -> List<Move>?) = apply {
            moves = Optional.ofNullable(init())
        }

        fun withOpponentMoves(init: Builder.() -> List<Move>?) = apply {
            opponentMoves = Optional.ofNullable(init())
        }

        fun withWinner(init: Builder.() -> Boolean?) = apply {
            winner = Optional.ofNullable(init())
        }

        fun build() = Board(this)
    }


    companion object {
        inline fun build(init: Builder.() -> Unit): Board =
                Builder().apply(init).build()
    }

    override fun toString(): String {
        return "{" +
                "\"gameId\":" + gameId +
                ", \"id\":" + id +
                ", \"ships\":" + pieces +
                ", \"opponentPieces\":" + opponentPieces +
                ", \"moves\":" + moves +
                ", \"opponentMoves\":" + opponentMoves +
                ", \"winner\":" + winner +
                '}'
    }
}

