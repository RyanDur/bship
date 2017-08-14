package com.bship.games.endpoints.game;

import com.bship.games.endpoints.cabinet.entity.Game;
import com.bship.games.endpoints.cabinet.entity.Move;
import com.bship.games.logic.definitions.GameRules;
import com.bship.games.endpoints.errors.exceptions.GameValidation;
import com.bship.games.endpoints.errors.exceptions.InvalidGame;
import com.bship.games.logic.GameLogic;
import com.bship.games.endpoints.cabinet.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private GameRepository repository;
    private GameLogic logic;

    @Autowired
    public GameService(GameRepository repository, GameLogic logic) {
        this.repository = repository;
        this.logic = logic;
    }

    public Game getNewGame(GameRules game) {
        return repository.create(game);
    }

    public Game placeMove(Long gameId, Move move) throws GameValidation {
        return repository.get(gameId)
                .map(logic.valid(move))
                .flatMap(logic.play(move))
                .map(logic.setNextTurn(move))
                .flatMap(game -> {
                    if (game.isOver()) return repository.delete(game);
                    else return repository.save(game);
                })
                .orElseThrow(InvalidGame::new);
    }
}