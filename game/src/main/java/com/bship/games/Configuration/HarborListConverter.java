package com.bship.games.Configuration;

import com.bship.games.logic.rules.PieceType;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HarborListConverter extends StdConverter<List<PieceType>, List<Map<String, Object>>> {


    @Override
    public List<Map<String, Object>> convert(List<PieceType> pieces) {
        return pieces.stream().map(ship -> new HashMap<String, Object>() {{
            put("name", ship.getName());
            put("size", ship.getSize());
        }}).collect(Collectors.toList());
    }
}
