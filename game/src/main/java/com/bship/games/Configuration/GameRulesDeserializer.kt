package com.bship.games.Configuration

import com.bship.games.logic.definitions.GameRules
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class GameRulesDeserializer : JsonDeserializer<GameRules>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): GameRules {
        val oc = p?.codec
        val node = oc?.readTree<JsonNode>(p)

        return GameRules.valueOf(node?.get("name")?.asText() ?: "")
    }
}
