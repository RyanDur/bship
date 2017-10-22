package com.bship.games.Configuration

import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.logic.definitions.Orientation
import com.bship.games.logic.definitions.PieceType
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class PieceDeserializer : JsonDeserializer<Piece>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Piece {
        val oc = p?.codec
        val node = oc?.readTree<JsonNode>(p)

        return Piece.build {
            withId {
                val id = node?.get("id")
                return@withId if (id?.isNull!!) Long.MIN_VALUE else id.asLong()
            }
            withBoardId { node?.get("boardId")?.asLong() }
            withType { node?.get("type")?.let { oc.treeToValue(it, PieceType::class.java) } }
            withPlacement { node?.get("placement")?.let { oc.treeToValue(it, Point::class.java) } }
            withOrientation { node?.get("orientation")?.let { oc.treeToValue(it, Orientation::class.java) } }
        }
    }
}