package com.bship.games;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BoardRepository {
    private JdbcTemplate template;

    public BoardRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Board> createBoards() {
        
        return Arrays.asList(new Board(), new Board());
    }
}
