package com.bship.games.Configuration;

import com.bship.games.logic.definitions.PieceType;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PieceListConverter extends StdConverter<List<PieceType>, List<Map<String, Object>>> {

    @Override
    public List<Map<String, Object>> convert(List<PieceType> pieces) {
        return pieces.stream().map(piece -> new HashMap<String, Object>() {{
            put("name", piece.getName());
            put("size", piece.getSize());
        }}).collect(Collectors.toList());
    }
}
