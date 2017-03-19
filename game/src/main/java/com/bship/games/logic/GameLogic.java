package com.bship.games.logic;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.exceptions.TurnCheck;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.LambdaExceptionUtil.rethrowFunction;
import static com.bship.games.util.Util.detectCollision;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
public class GameLogic {

    public Function<Board, Board> placementCheck(Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        return rethrowFunction(board -> {
            if (exists(board, ship)) throw new ShipExistsCheck();
            if (collision(board, ship)) throw new ShipCollisionCheck();
            return board;
        });
    }

    public Function<Game, Game> valid(Move move) throws TurnCheck, MoveCollision {
        return rethrowFunction(game -> {
            if (game.getTurn() != null && !move.getBoardId().equals(game.getTurn()))
                throw new TurnCheck();
            if (game.getBoards().stream().filter(currentBoard(move)).anyMatch(played(move)))
                throw new MoveCollision();
            return game;
        });
    }

    public Function<Game, Game> play(Move move) {
        return game -> {
            Board current = game.getBoards().stream().filter(currentBoard(move)).findFirst().get();
            Board other = game.getBoards().stream().filter(currentBoard(move).negate()).findFirst().get();
            Move playedMove = getPlayedMove(move, other);
            return getUpdatedGame(game, current, other, playedMove).get();
        };
    }

    public Function<Game, Game> setNextTurn(Move move) {
        return game -> {
            Board other = game.getBoards().stream().filter(currentBoard(move).negate()).findFirst().get();
            return game.copy().withTurn(other.getId()).build();
        };
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

    private boolean exists(Board board, Ship ship) {
        return ofNullable(ship).isPresent() && ofNullable(board)
                .map(Board::getShips)
                .map(Collection::stream)
                .filter(ships -> ships.filter(Ship::isPlaced)
                        .map(Ship::getType)
                        .anyMatch(type -> type.equals(ship.getType()))).isPresent();
    }

    private boolean collision(Board board, Ship ship) {
        return ofNullable(ship).isPresent() && ofNullable(board)
                .map(Board::getShips).map(Collection::stream)
                .filter(ships -> ships.anyMatch(savedShip ->
                        detectCollision(getRange(savedShip), getRange(ship))))
                .isPresent();
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

    private Predicate<Board> played(Move move) {
        return board -> board.getMoves().contains(move);
    }

    private Predicate<Board> currentBoard(Move move) {
        return boards -> boards.getId().equals(move.getBoardId());
    }

    private Function<Ship, Predicate<Ship>> recentlySunk = sunk -> ship -> !ship.getType().equals(sunk.getType());

    private Predicate<Ship> notSunk = ship -> !ship.isSunk();
}