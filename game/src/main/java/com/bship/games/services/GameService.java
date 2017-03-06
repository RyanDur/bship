package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.TurnCheck;
import com.bship.games.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class GameService {
    private GameRepository repository;
    private GameLogic logic;

    @Autowired
    public GameService(GameRepository repository, GameLogic logic) {
        this.repository = repository;
        this.logic = logic;
    }

    public Game getNewGame() {
        return repository.createGame();
    }

    public Game placeMove(Long gameId, Long boardId, Move move) throws MoveCollision, TurnCheck {
        Game game = repository.getGame(gameId)
                .filter(logic.turnCheck(boardId))
                .orElseThrow(TurnCheck::new);

        return logic.playMove(game, boardId, move)
                .flatMap(saveTurn(boardId))
                .orElse(null);
    }

    private Function<Game, Optional<Game>> saveTurn(Long boardId) {
        return game -> game.getBoards().stream()
                .map(Board::getId)
                .filter(logic.nextTurn(boardId))
                .map(save(game))
                .findFirst();
    }

    private Function<Long, Game> save(Game game) {
        return nextTurn -> repository.save(game.copy().withTurn(nextTurn).build());
    }
}