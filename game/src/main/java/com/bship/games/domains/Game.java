package com.bship.games.domains;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

@JsonDeserialize(builder = Game.Builder.class)
public class Game {
    private List<Board> boards;
    private Long id;

    private Game(Builder builder) {
        boards = builder.boards;
        id = builder.id;
    }

    public List<Board> getBoards() {
        return Optional.ofNullable(boards).orElse(emptyList());
    }

    public Long getId() {
        return id;
    }

    public Builder copy() {
        return builder().withId(id).withBoards(boards);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private Long id;
        private List<Board> boards;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withBoards(List<Board> boards) {
            this.boards = unmodifiableList(ofNullable(boards).orElse(emptyList()));
            return this;
        }

        public Game build() {
            return new Game(this);
        }
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

    @Override
    public String toString() {
        return "Game{" +
                "boards=" + boards +
                ", id=" + id +
                '}';
    }
}
