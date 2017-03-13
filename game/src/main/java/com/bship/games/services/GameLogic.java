package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.MoveCollision;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.Util.detectCollision;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class GameLogic {

    public Predicate<Game> turnCheck(BigInteger id) {
        return game -> !ofNullable(game).map(Game::getTurn).filter(turn -> !turn.equals(id)).isPresent();
    }

    public Predicate<BigInteger> nextTurn(BigInteger boardId) {
        return id -> ofNullable(boardId).isPresent() &&
                ofNullable(id).filter(currentId -> !currentId.equals(boardId)).isPresent();
    }

    public boolean exists(Board board, Ship ship) {
        return ofNullable(ship).isPresent() &&
                ofNullable(board).map(Board::getShips).map(Collection::stream)
                        .filter(ships -> ships.anyMatch(ship::equals)).isPresent();
    }

    public boolean collision(Board board, Ship ship) {
        return ofNullable(ship).isPresent() && ofNullable(board)
                .map(Board::getShips).map(Collection::stream)
                .filter(ships -> ships.anyMatch(savedShip ->
                        detectCollision(getRange(savedShip), getRange(ship))))
                .isPresent();
    }

    public Optional<Game> playMove(Game game, BigInteger boardId, Move move) throws MoveCollision {
        Map<Boolean, Board> boardMap = partitionBoards(game, boardId);
        Board current = boardMap.get(move.getBoardId().equals(boardId));
        Board other = boardMap.get(!move.getBoardId().equals(boardId));

        if (isAlreadyPlayed(current, move)) throw new MoveCollision();
        Move playedMove = getPlayedMove(move, other);
        return getUpdatedGame(game, current, other, playedMove);
    }

    private Optional<Game> getUpdatedGame(Game game, Board current, Board other, Move playedMove) {
        return getSunk(getMoves(current, playedMove), other)
                .map(updateBoardsWithSunkShip(game, current, other, playedMove))
                .orElse(updateBoardsWithoutSunkShip(game, current, other, playedMove));
    }

    private Optional<Game> updateBoardsWithoutSunkShip(Game game, Board current, Board other, Move playedMove) {
        return of(game).map(Game::copy).map(copy -> copy.withBoards(asList(
                current.copy()
                        .addMove(playedMove)
                        .build(),
                other.copy()
                        .addOpponentMove(playedMove)
                        .build()
        )).build());
    }

    private Function<Ship, Optional<Game>> updateBoardsWithSunkShip(Game game, Board current, Board other, Move playedMove) {
        return sunk -> of(game).map(Game::copy).map(copy -> copy.withBoards(asList(
                current.copy()
                        .addMove(playedMove)
                        .addOpponentShip(sunk)
                        .build(),
                other.copy()
                        .addOpponentMove(playedMove)
                        .withShips(without(other, recentlySunk.apply(sunk)))
                        .addShip(sunk)
                        .build()
        )).build());
    }

    private List<Ship> without(Board other, Predicate<Ship> test) {
        return other.getShips().stream().filter(test).collect(toList());
    }

    private Move getPlayedMove(Move move, Board other) {
        MoveStatus status = getStatus(move, other.getShips());
        return move.copy().withStatus(status).build();
    }

    private List<Point> getMoves(Board current, Move playedMove) {
        return current.copy()
                .addMove(playedMove)
                .build()
                .getMoves()
                .stream()
                .map(Move::getPoint)
                .collect(toList());
    }

    private Optional<Ship> getSunk(List<Point> moves, Board other) {
        List<Ship> unSunk = without(other, notSunk);
        return unSunk.stream()
                .filter(ship -> moves.containsAll(getRange(ship)))
                .findFirst()
                .map(Ship::copy)
                .map(ship -> ship.withSunk(true))
                .map(Ship.Builder::build);
    }

    private boolean isAlreadyPlayed(Board current, Move move) {
        return current.getMoves().stream()
                .map(Move::getPoint)
                .collect(toList())
                .contains(move.getPoint());
    }

    private Map<Boolean, Board> partitionBoards(Game game, BigInteger boardId) {
        return game.getBoards()
                .stream()
                .collect(partitioningBy(board -> board.getId().equals(boardId)))
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().findFirst().get()));
    }

    private List<Point> getRange(Ship ship) {
        return pointsRange(ship.getStart(), ship.getEnd());
    }

    private MoveStatus getStatus(Move move, List<Ship> ships) {
        return ships.stream()
                .map(this::getRange)
                .flatMap(Collection::stream)
                .filter(point -> point.equals(move.getPoint()))
                .findAny().map(p -> HIT).orElse(MISS);
    }

    private Predicate<Ship> notSunk = ship -> !ship.isSunk();

    private Function<Ship, Predicate<Ship>> recentlySunk = sunk -> ship -> !ship.getType().equals(sunk.getType());
}