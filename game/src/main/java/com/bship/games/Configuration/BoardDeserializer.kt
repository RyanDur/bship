package com.bship.games.Configuration

import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Move
import com.bship.games.endpoints.cabinet.entity.Piece
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class BoardDeserializer : JsonDeserializer<Board>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Board {
        val oc = p?.codec
        val node = oc?.readTree<JsonNode>(p)

        return Board.build {
            withId { node?.get("id")?.asLong() }
            withGameId { node?.get("gameId")?.asLong() }
            withWinner { node?.get("winner")?.asBoolean() }
            withMoves { node?.get("moves")?.map { oc.treeToValue(it, Move::class.java) } }
            withOpponentMoves { node?.get("opponentMoves")?.map { oc.treeToValue(it, Move::class.java) } }
            withPieces { node?.get("pieces")?.map { oc.treeToValue(it, Piece::class.java) } }
            withOpponentPieces { node?.get("opponentPieces")?.map { oc.treeToValue(it, Piece::class.java) } }
        }
    }
}