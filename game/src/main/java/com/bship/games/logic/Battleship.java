package com.bship.games.logic;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.MoveStatus;
import com.bship.games.domains.Piece;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.exceptions.TurnCheck;
import com.bship.games.util.Util;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.bship.games.domains.MoveStatus.HIT;
import static com.bship.games.domains.MoveStatus.MISS;
import static com.bship.games.util.LambdaExceptionUtil.rethrowFunction;
import static com.bship.games.util.Util.detectCollision;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class Battleship implements GameLogic {

    private static final boolean CURRENT_BOARD = true;
    private static final boolean OPPONENT_BOARD = false;
    private static final boolean SUNK = true;

    @Override
    public Function<Board, Board> placementCheck(Piece piece) throws ShipExistsCheck, ShipCollisionCheck {
        return rethrowFunction(board -> {
            if (exists(piece, board)) throw new ShipExistsCheck();
            if (collision(piece, board)) throw new ShipCollisionCheck();
            return board;
        });
    }

    @Override
    public Function<Game, Game> valid(Move move) throws TurnCheck, MoveCollision {
        return rethrowFunction(game -> {
            if (wrongTurn(move, game)) throw new TurnCheck();
            if (collision(move, game)) throw new MoveCollision();
            return game;
        });
    }

    @Override
    public Function<Game, Optional<Game>> play(Move move) {
        return game -> ofNullable(game)
                .map(Game::getBoards)
                .map(updateBoards(move))
                .map(update(game));
    }

    @Override
    public Function<Game, Game> setNextTurn(Move move) {
        return game -> {
            if (game.isOver()) return game.copy().withTurn(null).build();
            Board other = partitionBoards(move).apply(game.getBoards()).get(OPPONENT_BOARD);
            return game.copy().withTurn(other.getId()).build();
        };
    }

    private Function<List<Board>, Game> update(Game game) {
        return boards -> game.copy()
                .withBoards(boards)
                .withOver(boards.stream().anyMatch(Board::isWinner))
                .build();
    }

    private Function<List<Board>, List<Board>> updateBoards(Move move) {
        return boards -> ofNullable(boards)
                .map(partitionBoards(move))
                .map(boardMap -> {
                    Board current = boardMap.get(CURRENT_BOARD);
                    Board opponent = boardMap.get(OPPONENT_BOARD);

                    MoveStatus status = getStatus(move, opponent.getPieces());
                    Move newMove = move.copy().withStatus(status).build();

                    Board currentBuild = current.copy().addMove(newMove).build();
                    Board opponentBuild = opponent.copy().addOpponentMove(newMove).build();

                    return asList(
                            updateBoard(currentBuild, opponentBuild),
                            updateBoard(opponentBuild, currentBuild)
                    );
                }).orElse(emptyList());
    }

    private Board updateBoard(Board current, Board opponent) {
        List<Piece> sunk = getSunk(sinkShips(current, opponent));
        List<Piece> ships = sinkShips(opponent, current);
        return ofNullable(current).map(Board::copy)
                .map(board -> board.withPieces(ships))
                .map(board -> board.withOpponentPieces(sunk))
                .map(board -> board.withWinner(ships.size() == sunk.size()))
                .map(Board.Builder::build)
                .orElse(current);
    }

    private List<Piece> sinkShips(Board current, Board opponent) {
        return opponent.getPieces().stream().map(sinkShip(current.getMoves())).collect(toList());
    }

    private List<Piece> getSunk(List<Piece> pieces) {
        return pieces.stream().filter(Piece::isSunk).collect(Collectors.toList());
    }

    private Function<Piece, Piece> sinkShip(List<Move> moves) {
        return piece -> ofNullable(piece)
                .filter(isSunk(moves))
                .map(Piece::copy)
                .map(p -> p.withSunk(SUNK))
                .map(Piece.Builder::build)
                .orElse(piece);
    }

    private Predicate<Piece> isSunk(List<Move> moves) {
        return piece -> ofNullable(piece)
                .map(Util::pointsRange)
                .filter(points -> moves.stream().map(Move::getPoint).collect(toList()).containsAll(points))
                .isPresent();
    }

    private boolean exists(Piece piece, Board board) {
        return ofNullable(piece).isPresent() && ofNullable(board)
                .map(Board::getPieces)
                .map(Collection::stream)
                .filter(ships -> ships.filter(Util::isPlaced)
                        .map(Piece::getType)
                        .anyMatch(type -> type.equals(piece.getType()))).isPresent();
    }

    private boolean collision(Piece piece, Board board) {
        return ofNullable(piece).isPresent() && ofNullable(board)
                .map(Board::getPieces).map(Collection::stream)
                .filter(ships -> ships.anyMatch(savedPiece ->
                        detectCollision(savedPiece, piece)))
                .isPresent();
    }

    private MoveStatus getStatus(Move move, List<Piece> pieces) {
        return pieces.stream()
                .map(Util::pointsRange)
                .flatMap(Collection::stream)
                .filter(point -> point.equals(move.getPoint()))
                .findAny().map(p -> HIT).orElse(MISS);
    }

    private boolean collision(Move move, Game game) {
        return ofNullable(game).map(Game::getBoards)
                .map(partitionBoards(move))
                .map(boards -> boards.get(CURRENT_BOARD))
                .filter(board -> board.getMoves().contains(move))
                .isPresent();
    }

    private boolean wrongTurn(Move move, Game game) {
        return nonNull(game.getTurn()) && !game.getTurn().equals(move.getBoardId());
    }

    private Function<List<Board>, Map<Boolean, Board>> partitionBoards(Move move) {
        return boards -> boards.stream().collect(toMap(
                board -> board.getId().equals(move.getBoardId()),
                Function.identity()));
    }
}