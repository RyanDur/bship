package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
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
import java.util.stream.Stream;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.Util.addTo;
import static com.bship.games.util.Util.detectCollision;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Collections.emptyList;
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

    public Board placeMove(Long gameId, Long boardId, Point point) throws MoveCollision {
        List<Move> playedMoves = moveRepository.getAll(boardId).orElse(emptyList());
        of(playedMoves).filter(notAlreadyPlaced(point)).orElseThrow(MoveCollision::new);

        Optional<List<Ship>> ships = shipRepository.getAll(boardId);
        List<Move> moves = ships.flatMap(createMove(boardId, point)).map(addTo(playedMoves)).orElse(emptyList());
        List<Ship> sunk = ships.flatMap(getSunkenShips(moves)).orElse(emptyList());

        return Board.builder().withMoves(moves)
                .withShips(sunk).withId(boardId).build();
    }

    private Predicate<List<Move>> notAlreadyPlaced(Point point) {
        return moves -> moves.stream().map(Move::getPoint).noneMatch(point::equals);
    }

    private Function<List<Ship>, Optional<Move>> createMove(Long boardId, Point point) {
        return ships -> moveRepository.create(boardId, point, getStatus(point, ships));
    }

    private MoveStatus getStatus(Point point, List<Ship> ships) {
        return ships.stream().map(getRange).flatMap(Collection::stream)
                .filter(point::equals).findAny().map(p -> HIT).orElse(MISS);
    }

    private Function<List<Ship>, Optional<List<Ship>>> getSunkenShips(List<Move> moves) {
        return ships -> of(ships).map(unSunk).flatMap(collectFirst(sinkingShip(moves))).map(addTo(sunken(ships)));
    }

    private List<Ship> sunken(List<Ship> ships) {
        return ships.stream().filter(Ship::isSunk).collect(toList());
    }

    private Function<Ship, Optional<Ship>> sinkingShip(List<Move> moves) {
        return ship -> of(ship).filter(isSunk(moves)).flatMap(updateSunkShip);
    }

    private Predicate<Ship> isSunk(List<Move> moves) {
        return ship -> of(ship).map(getRange).filter(toPoints(moves)::containsAll).isPresent();
    }

    private List<Point> toPoints(List<Move> moves) {
        return moves.stream().map(Move::getPoint).collect(toList());
    }

    private boolean shipExists(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(ship::equals);
    }

    private boolean collision(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip ->
                detectCollision(getRange.apply(savedShip), getRange.apply(ship)));
    }

    private <T> Function<Stream<T>, Optional<T>> collectFirst(Function<T, Optional<T>> function) {
        return stream -> stream.map(function).filter(Optional::isPresent).map(Optional::get).findFirst();
    }

    private Function<Ship, Optional<Ship>> updateSunkShip =
            ship -> shipRepository.update(ship.copy().withSunk(true).build());

    private Function<Ship, List<Point>> getRange =
            ship -> pointsRange(ship.getStart(), ship.getEnd());

    private Function<List<Ship>, Stream<Ship>> unSunk =
            ships -> ships.stream().filter(ship -> !ship.isSunk());
}