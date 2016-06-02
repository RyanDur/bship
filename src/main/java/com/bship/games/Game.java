package com.bship.games;

import java.util.List;

public class Game {
    private List<Board> boards;

    public Game(List<Board> boards) {
        this.boards = boards;
    }

    public Game() {

    }

    public List<Board> getBoards() {
        return boards;
    }
}
