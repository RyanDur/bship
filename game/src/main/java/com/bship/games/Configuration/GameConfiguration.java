package com.bship.games.Configuration;

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
