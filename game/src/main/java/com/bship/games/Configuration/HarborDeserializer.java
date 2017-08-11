package com.bship.games.Configuration;

import com.bship.games.domains.Harbor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import static com.bship.games.domains.Harbor.INVALID_SHIP;

public class HarborDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode jsonNode = p.readValueAsTree();
        if (jsonNode == null) throw ctxt.mappingException("Cannot deserialize empty value");
        Harbor piece = Harbor.create(jsonNode.textValue());
        if (piece != INVALID_SHIP) return piece;

        JsonNode value = jsonNode.findValue("name");
        if (value == null) return INVALID_SHIP;

        piece = Harbor.create(value.textValue());

        int size = jsonNode.findValue("size").asInt();
        if (piece == INVALID_SHIP || piece.getSize() != size) return INVALID_SHIP;
        return piece;
    }
}
