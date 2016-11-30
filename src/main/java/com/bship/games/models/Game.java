package com.bship.games.models;

import java.util.List;

public class Game {
    private List<Board> boards;
    private Long id;

    public List<Board> getBoards() {
        return boards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (boards != null ? !boards.equals(game.boards) : game.boards != null) return false;
        return id != null ? id.equals(game.id) : game.id == null;
    }

    @Override
    public int hashCode() {
        int result = boards != null ? boards.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }
}
