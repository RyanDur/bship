package com.bship.games.logic;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Piece;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.exceptions.TurnCheck;

import java.util.Optional;
import java.util.function.Function;

public interface GameLogic {
    Function<Board, Board> placementCheck(Piece piece) throws ShipExistsCheck, ShipCollisionCheck;

    Function<Game, Game> valid(Move move) throws TurnCheck, MoveCollision;

    Function<Game, Optional<Game>> play(Move move);

    Function<Game, Game> setNextTurn(Move move);
}
