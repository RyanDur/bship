package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.ofNullable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(builder = Game.Builder.class)
public class Game {
    private final List<Board> boards;
    private final Long id;
    private final Long turn;
    private final boolean over;
    private final GameRules rules;

    private Game(Builder builder) {
        boards = builder.boards;
        id = builder.id;
        turn = builder.turn;
        over = builder.over;
        rules = builder.rules;
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

    public boolean isOver() {
        return over;
    }

    public GameRules getRules() {
        return rules;
    }

    public Builder copy() {
        return builder()
                .withId(id)
                .withBoards(boards)
                .withRules(rules)
                .withTurn(turn)
                .withOver(over);
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
                Objects.equals(this.over, that.over) &&
                Objects.equals(this.turn, that.turn) &&
                Objects.equals(this.rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boards, id, over, turn);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"boards\": " + boards)
                .add("\"id\":" + id)
                .add("\"over\":" + over)
                .add("\"turn\":" + turn)
                .add("\"rules\":" + rules)
                .toString();
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private Long id;
        private List<Board> boards;
        private Long turn;
        public boolean over;
        private GameRules rules;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withBoards(List<Board> boards) {
            this.boards = unmodifiableList(ofNullable(boards).orElse(emptyList()));
            return this;
        }

        public Builder withOver(boolean over) {
            this.over = over;
            return this;
        }

        public Builder withRules(GameRules rules) {
            this.rules = rules;
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
}
