package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.MoveRepository;
import com.bship.games.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.Util.addTo;
import static com.bship.games.util.Util.detectCollision;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Service
public class GameService {
    private BoardRepository boardRepository;
    private GameRepository gameRepository;
    private ShipRepository shipRepository;
    private MoveRepository moveRepository;

    @Autowired
    public GameService(BoardRepository boardRepository,
                       GameRepository gameRepository,
                       ShipRepository shipRepository,
                       MoveRepository moveRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
        this.moveRepository = moveRepository;
    }

    public Game getNewGame() {
        Game game = gameRepository.createGame();
        List<Board> boards = boardRepository.create(game);
        return game.copy().withBoards(boards).build();
    }

    public Board placeShip(Long boardId, Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        Board board = boardRepository.get(boardId);
        if (shipExists(board.getShips(), ship)) throw new ShipExistsCheck();
        if (collision(board.getShips(), ship)) throw new ShipCollisionCheck();

        Optional<Ship> createdShip = shipRepository.create(ship, boardId);
        return createdShip.map(newShip -> board.copy().addShip(newShip).build()).orElse(null);
    }

    public Optional<Board> placeMove(Long gameId, Long boardId, Point point) throws MoveCollision {
        Optional<List<Move>> moves = moveRepository.getAll(boardId);
        if (isPresent(point, moves.orElse(emptyList()))) throw new MoveCollision();

        return shipRepository.getAll(boardId)
                .map(getBoard(boardId, point, moves.orElse(null)))
                .orElse(empty());
    }

    private boolean isPresent(Point point, List<Move> moves) {
        return moves.stream().map(Move::getPoint).anyMatch(point::equals);
    }

    private Function<List<Ship>, Optional<Board>> getBoard(Long boardId, Point point, List<Move> moves) {
        return ships -> of(ships).map(getNewMoveSet(boardId, point, moves))
                .flatMap(playNewMoveSet(boardId, newMoves -> getSunkenShips(newMoves, ships).orElse(emptyList())));
    }

    private Function<List<Ship>, List<Move>> getNewMoveSet(Long boardId, Point point, List<Move> moves) {
        return ships -> getMove(boardId, point, ships)
                .flatMap(move -> of(move).map(m -> addTo(moves, m)))
                .orElse(emptyList());
    }

    private Function<List<Move>, Optional<Board>> playNewMoveSet(Long boardId, Function<List<Move>, List<Ship>> sunkenShips) {
        return newMoves -> of(Board.builder().withMoves(newMoves)
                .withShips(sunkenShips.apply(newMoves)).withId(boardId).build());
    }

    private Optional<Move> getMove(Long boardId, Point point, List<Ship> ships) {
        return moveRepository.create(boardId, point, ships.stream()
                .map(getRange)
                .flatMap(Collection::stream)
                .filter(point::equals)
                .findAny().map(p -> HIT).orElse(MISS));
    }

    private Optional<List<Ship>> getSunkenShips(List<Move> moves, List<Ship> ships) {
        List<Point> points = moves.stream().map(Move::getPoint).collect(toList());
        return ships.stream().filter(notSunk).map(sinkingShip(points))
                .filter(Optional::isPresent).map(Optional::get).findFirst()
                .map(sunk -> addTo(ships.stream().filter(Ship::isSunk).collect(toList()), sunk));
    }

    private Function<Ship, Optional<Ship>> sinkingShip(List<Point> points) {
        return ship -> of(points).filter(pts -> isSunk(pts, ship))
                .flatMap(p -> shipRepository.update(ship.copy().withSunk(true).build()));
    }

    private boolean isSunk(List<Point> points, Ship ship) {
        return of(ship).map(getRange).filter(points::containsAll).isPresent();
    }

    private boolean shipExists(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip -> savedShip.getType().equals(ship.getType()));
    }

    private boolean collision(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip -> detectCollision(
                getRange.apply(savedShip), getRange.apply(ship)));
    }

    private Function<Ship, List<Point>> getRange = s ->
            pointsRange(s.getStart(), s.getEnd());

    private Predicate<Ship> notSunk = ship ->
            !ship.isSunk();
}