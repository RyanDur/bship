package com.bship.games.Configuration;

import com.bship.games.domains.Harbor;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HarborListConverter extends StdConverter<List<Harbor>, List<Map<String, Object>>> {


    @Override
    public List<Map<String, Object>> convert(List<Harbor> harbor) {
        return harbor.stream().map(ship -> new HashMap<String, Object>() {{
            put("name", ship.name());
            put("size", ship.getSize());
        }}).collect(Collectors.toList());
    }
}
