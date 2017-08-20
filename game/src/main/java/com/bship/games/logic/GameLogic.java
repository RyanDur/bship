package com.bship.games.logic;

import com.bship.games.endpoints.cabinet.entity.Board;
import com.bship.games.endpoints.cabinet.entity.Game;
import com.bship.games.endpoints.cabinet.entity.Move;
import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.game.errors.MoveCollision;
import com.bship.games.endpoints.board.errors.exceptions.ShipCollisionCheck;
import com.bship.games.endpoints.board.errors.exceptions.ShipExistsCheck;
import com.bship.games.endpoints.game.errors.TurnCheck;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface GameLogic {
    Predicate<Board> placementCheck(List<Piece> pieces) throws ShipExistsCheck, ShipCollisionCheck;

    Predicate<Game> valid(Move move) throws TurnCheck, MoveCollision;

    Function<Game, Optional<Game>> play(Move move);

    Function<Game, Game> setNextTurn(Move move);
}
