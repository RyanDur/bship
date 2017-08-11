package com.bship.games.domains;

import com.bship.games.Configuration.HarborListConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static com.bship.games.domains.BoardDimensions.BATTLESHIP_BOARD;
import static com.bship.games.domains.Direction.NONE;
import static com.bship.games.domains.Harbor.INVALID_SHIP;
import static java.util.stream.Collectors.toList;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameRules {
    BATTLESHIP(2, 1, BATTLESHIP_BOARD, remove(INVALID_SHIP, Harbor.values()), remove(NONE, Direction.values()));

    private final int numberOfPlayers;
    private final int movesPerTurn;
    private final BoardDimensions boardDimensions;
    @JsonSerialize(converter = HarborListConverter.class)
    private final List<Harbor> pieces;
    private final List<Direction> pieceOrientations;

    GameRules(int numberOfPlayers,
              int movesPerTurn,
              BoardDimensions boardDimensions,
              List<Harbor> pieces,
              List<Direction> pieceOrientations) {
        this.numberOfPlayers = numberOfPlayers;
        this.movesPerTurn = movesPerTurn;
        this.boardDimensions = boardDimensions;
        this.pieces = pieces;
        this.pieceOrientations = pieceOrientations;
    }

    public String getName() {
        return name();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getMovesPerTurn() {
        return movesPerTurn;
    }

    public BoardDimensions getBoardDimensions() {
        return boardDimensions;
    }

    public List<Harbor> getPieces() {
        return pieces;
    }

    public List<Direction> getPieceOrientations() {
        return pieceOrientations;
    }

    private static <T> List<T> remove(T elem, T[] elems) {
        return Arrays.stream(elems).filter(it -> it != elem).collect(toList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("\"name\": " + "\"" + name() + "\"")
                .add("\"boardDimensions\": " + boardDimensions)
                .add("\"movesPerTurn\": " + movesPerTurn)
                .add("\"numberOfPlayers\": " + numberOfPlayers)
                .add("\"pieceOrientations\": " + pieceOrientations)
                .add("\"pieces\": " + pieces)
                .toString();
    }


}
