package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.MoveCollision;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Predicate<Game> turnCheck(Long id) {
        return game -> !ofNullable(game)
                .map(Game::getTurn)
                .filter(turn -> !turn.equals(id))
                .isPresent();
    }

    public Predicate<Long> nextTurn(Long boardId) {
        return id -> ofNullable(boardId).isPresent() && ofNullable(id)
                .filter(currentId -> !currentId.equals(boardId))
                .isPresent();
    }

    public boolean exists(Board board, Ship ship) {
        return ofNullable(ship).isPresent() && ofNullable(board)
                .map(Board::getShips)
                .map(Collection::stream)
                .filter(ships -> ships.anyMatch(ship::equals))
                .isPresent();
    }

    public boolean collision(Board board, Ship ship) {
        return ofNullable(ship).isPresent() && ofNullable(board)
                .map(Board::getShips)
                .map(Collection::stream)
                .filter(ships -> ships.anyMatch(savedShip ->
                        detectCollision(getRange(savedShip), getRange(ship))))
                .isPresent();
    }

    public Optional<Game> playMove(Game game, Long boardId, Move move) throws MoveCollision {
        Map<Boolean, Board> boardMap = partitionBoards(game, boardId);
        Board current = boardMap.get(move.getBoardId().equals(boardId));
        Board other = boardMap.get(!move.getBoardId().equals(boardId));

        if (isAlreadyPlayed(current, move)) throw new MoveCollision();
        Move playedMove = getPlayedMove(move, other);
        List<Point> moves = getMoves(current, playedMove);

        List<Ship> unSunk = other.getShips().stream().filter(ship -> !ship.isSunk()).collect(toList());

        Optional<Ship> justSunk = getSunk(moves, unSunk);

        if (justSunk.isPresent()) {
            List<Ship> ships = other.getShips().stream().filter(o -> !o.getType().equals(justSunk.get().getType())).collect(toList());
            return of(game).map(Game::copy).map(copy -> copy.withBoards(asList(
                    current.copy().addMove(playedMove).addOpponentShip(justSunk.get()).build(),
                    other.copy().withShips(ships).addShip(justSunk.get()).addOpponentMove(playedMove).build()
            )).build());
        }
        return of(game).map(Game::copy).map(copy -> copy.withBoards(asList(
                current.copy().addMove(playedMove).build(),
                other.copy().addOpponentMove(playedMove).build()
        )).build());
    }

    public Move getPlayedMove(Move move, Board other) {
        MoveStatus status = getStatus(move, other.getShips());
        return move.copy().withStatus(status).build();
    }

    public List<Point> getMoves(Board current, Move playedMove) {
        return current.copy()
                .addMove(playedMove)
                .build()
                .getMoves()
                .stream()
                .map(Move::getPoint)
                .collect(toList());
    }

    public Optional<Ship> getSunk(List<Point> moves, List<Ship> unSunk) {
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

    private Map<Boolean, Board> partitionBoards(Game game, Long boardId) {
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

//    private Function<List<Ship>, Optional<List<Ship>>> getSunkenShips(List<Move> moves) {
//        return ships -> of(ships).map(unSunk).flatMap(collectFirst(sinkingShip(moves))).map(addTo(sunken(ships)));
//    }
//
//    private List<Ship> sunken(List<Ship> ships) {
//        return ships.stream().filter(Ship::isSunk).collect(toList());
//    }
//
//    private Predicate<Ship> isSunk(List<Move> moves) {
//        return ship -> of(ship).map(this::getRange).filter(toPoints(moves)::containsAll).isPresent();
//    }
//
//    private List<Point> toPoints(List<Move> moves) {
//        return moves.stream().map(Move::getPoint).collect(toList());
//    }
//
//    private <T> Function<Stream<T>, Optional<T>> collectFirst(Function<T, Optional<T>> function) {
//        return stream -> stream.map(function).filter(Optional::isPresent).map(Optional::get).findFirst();
//    }
//
//    private Function<Ship, Optional<Ship>> sinkingShip(List<Move> moves) {
//        return ship -> of(ship).filter(isSunk(moves)).flatMap(updateSunkShip);
//    }
//
//    private Function<List<Ship>, Stream<Ship>> unSunk =
//            ships -> ships.stream().filter(ship -> !ship.isSunk());
//
//    private Function<Ship, Optional<Ship>> updateSunkShip =
//            ship -> of(ship.copy().withSunk(true).build());

    //    private Optional<Game> playMove(Game game, Point point, Long boardId) throws MoveCollision {
//        Board currentBoard = getCurrentBoard(boardId, game).orElse(null);
//
//        List<Move> playedMoves = of(currentBoard).map(Board::getMoves)
//                .filter(notAlreadyPlaced(point)).orElseThrow(MoveCollision::new);
//
//        Optional<List<Ship>> ships = of(currentBoard.getShips());
//
//        List<Move> moves = ships.flatMap(createMove(currentBoard.getId(), point))
//                .map(addTo(playedMoves)).orElse(emptyList());
//
//        List<Ship> sunk = ships.flatMap(getSunkenShips(moves)).orElse(emptyList());
//        Board board = currentBoard.copy().withMoves(moves).withShips(sunk).build();
//        List<Board> boardList = game.getBoards().stream().filter(b -> !b.getId().equals(board.getId()))
//                .flatMap(b -> Stream.of(b, board)).collect(toList());
//
//        return of(game.copy().withBoards(boardList).build());
//    }
//
//    private Optional<Board> getCurrentBoard(Long boardId, Game game) {
//        return game.getBoards().stream().filter(o -> boardId.equals(o.getId())).findFirst();
//    }
//
//
//    private Predicate<List<Move>> notAlreadyPlaced(Point point) {
//        return moves -> moves.stream().map(Move::getPoint).noneMatch(point::equals);
//    }
//
//    private Function<List<Ship>, Optional<Move>> createMove(Long boardId, Point point) {
//        return ships -> moveRepository.create(boardId, point, getStatus(point, ships));
//    }
//
//    private MoveStatus getStatus(Point point, List<Ship> ships) {
//        return ships.stream().map(getRange).flatMap(Collection::stream)
//                .filter(point::equals).findAny().map(p -> HIT).orElse(MISS);
//    }
//
//    private Function<List<Ship>, Optional<List<Ship>>> getSunkenShips(List<Move> moves) {
//        return ships -> of(ships).map(unSunk).flatMap(collectFirst(sinkingShip(moves))).map(addTo(sunken(ships)));
//    }
//
//    private List<Ship> sunken(List<Ship> ships) {
//        return ships.stream().filter(Ship::isSunk).collect(toList());
//    }
//
//    private Function<Ship, Optional<Ship>> sinkingShip(List<Move> moves) {
//        return ship -> of(ship).filter(isSunk(moves)).flatMap(updateSunkShip);
//    }
//
//    private Predicate<Ship> isSunk(List<Move> moves) {
//        return ship -> of(ship).map(getRange).filter(toPoints(moves)::containsAll).isPresent();
//    }
//
//    private List<Point> toPoints(List<Move> moves) {
//        return moves.stream().map(Move::getPoint).collect(toList());
//    }
//
//
//    private <T> Function<Stream<T>, Optional<T>> collectFirst(Function<T, Optional<T>> function) {
//        return stream -> stream.map(function).filter(Optional::isPresent).map(Optional::get).findFirst();
//    }
//
//    private Function<Ship, Optional<Ship>> updateSunkShip =
//            ship -> shipRepository.update(ship.copy().withSunk(true).build());
//
//    private Function<List<Ship>, Stream<Ship>> unSunk =
//            ships -> ships.stream().filter(ship -> !ship.isSunk());
}
