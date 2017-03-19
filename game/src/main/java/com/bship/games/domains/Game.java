package com.bship.games.domains;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

@JsonDeserialize(builder = Game.Builder.class)
public class Game {
    private final List<Board> boards;
    private final Long id;
    private final Long turn;

    private Game(Builder builder) {
        boards = builder.boards;
        id = builder.id;
        turn = builder.turn;
    }

    public List<Board> getBoards() {
        return Optional.ofNullable(boards).orElse(emptyList());
    }

    public Long getId() {
        return id;
    }

    public Long getTurn() {
        return turn;
    }

    public Builder copy() {
        return builder().withId(id).withBoards(boards);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game that = (Game) o;

        return Objects.equals(this.boards, that.boards) &&
                Objects.equals(this.id, that.id) &&
                Objects.equals(this.turn, that.turn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boards, id, turn);
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private Long id;
        private List<Board> boards;
        private Long turn;

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

        public Builder withTurn(Long turn) {
            this.turn = turn;
            return this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"boards\":" + getBoards() +
                ", \"id\":" + getId() +
                ", \"turn\":" + getTurn() +
                '}';
    }
}
