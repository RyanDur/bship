package com.bship.games.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class GameConfiguration {

    @Bean
    public NamedParameterJdbcTemplate template(DataSource source) {
        return new NamedParameterJdbcTemplate(source);
    }
}
