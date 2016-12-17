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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.Util.detectCollision;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.empty;

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

        Ship createdShip = shipRepository.create(ship, boardId);

        return board.copy().addShip(createdShip).build();
    }

    public Board placeMove(Long gameId, Long boardId, Point point) throws MoveCollision {
        Optional<List<Move>> moves = moveRepository.getAll(boardId);
        if (isPresent(pointEquals.apply(point), moves.orElse(emptyList()))) throw new MoveCollision();

        List<Ship> ships = shipRepository.getAll(boardId);
        Optional<Move> move = getMove(boardId, point, ships);
        List<Move> newMoveList = addTo(moves.orElse(emptyList()), move.orElse(null));
        Optional<List<Ship>> sunk = of(newMoveList).map(newMoves -> getSunkenShips(newMoves, ships));

        return Board.builder()
                .withMoves(newMoveList)
                .withShips(sunk.orElse(emptyList()))
                .withId(boardId).build();
    }

    private <T> List<T> addTo(List<T> list, T elem) {
        return concat(ofNullable(list).map(Collection::stream).orElse(empty()), Stream.of(elem))
                .filter(Objects::nonNull)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private <T> boolean isPresent(Predicate<T> predicate, List<T> list) {
        return list.stream().filter(predicate).count() > 0;
    }

    private Function<Point, Predicate<Move>> pointEquals = point -> move ->
            point.equals(move.getPoint());

    private List<Ship> getSunkenShips(List<Move> moves, List<Ship> ships) {
        List<Point> points = moves.stream().map(Move::getPoint).collect(toList());
        Optional<Ship> newSunk = ships.stream()
                .filter(notSunk)
                .filter(getSunkenShip(points))
                .map(updateShip).findFirst();
        List<Ship> sunk = ships.stream().filter(Ship::isSunk).collect(toList());
        newSunk.ifPresent(sunk::add);
        return sunk;
    }

    private Optional<Move> getMove(Long boardId, Point point, List<Ship> ships) {
        return moveRepository.create(boardId, point, ships.stream()
                .map(shipRange)
                .flatMap(Collection::stream)
                .filter(point::equals)
                .findAny().map(p -> HIT).orElse(MISS));
    }

    private Function<Ship, List<Point>> shipRange = ship ->
            pointsRange(ship.getStart(), ship.getEnd());

    private boolean shipExists(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip -> savedShip.getType().equals(ship.getType()));
    }

    private boolean collision(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip -> detectCollision(
                shipRange.apply(savedShip), shipRange.apply(ship)));
    }

    private Function<Ship, Ship> updateShip = ship -> shipRepository
            .update(ship.copy().withSunk(true).build());

    private Function<Ship, List<Point>> getRange = s ->
            pointsRange(s.getStart(), s.getEnd());

    private Predicate<Ship> notSunk = ship ->
            !ship.isSunk();

    private Predicate<Ship> getSunkenShip(List<Point> points) {
        return ship -> of(ship).map(getRange).filter(points::containsAll).isPresent();
    }
}
