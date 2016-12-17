package com.bship.games.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Move {

    @JsonIgnore
    private Long boardId;

    @JsonIgnore
    private Long id;

    private Point point;
    private MoveStatus status;

    public Move() {}

    private Move(Builder builder) {
        this.point = builder.point;
        this.status = builder.status;
        this.id = builder.id;
        this.boardId = builder.boardId;
    }

    public Point getPoint() {
        return point;
    }

    public MoveStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder copy() {
        return builder().withId(id)
                .withBoardId(boardId)
                .withPoint(point)
                .withStatus(status);
    }

    public static class Builder {
        private Point point;
        private MoveStatus status;
        private Long id;
        private Long boardId;

        public Move build() {
            return new Move(this);
        }

        public Builder withPoint(Point point) {
            this.point = point;
            return this;
        }

        public Builder withStatus(MoveStatus status) {
            this.status = status;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withBoardId(Long boardId) {
            this.boardId = boardId;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (boardId != null ? !boardId.equals(move.boardId) : move.boardId != null) return false;
        if (id != null ? !id.equals(move.id) : move.id != null) return false;
        return this.point != null ? this.point.equals(move.point) : move.point == null;
    }

    @Override
    public int hashCode() {
        int result = boardId != null ? boardId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Move{" +
                "boardId=" + boardId +
                ", id=" + id +
                ", status=" + status +
                ", point=" + point +
                '}';
    }
}
