package com.bship.games.models;

public class Board {
    private Long id;

    private Board(Builder builder) {
        id = builder.id;
    }

    public Long getId() {
        return id;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return id != null ? id.equals(board.id) : board.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
