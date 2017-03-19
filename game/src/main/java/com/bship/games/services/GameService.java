package com.bship.games.services;

import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.exceptions.GameValidation;
import com.bship.games.exceptions.InvalidGame;
import com.bship.games.logic.GameLogic;
import com.bship.games.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

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
        return repository.create();
    }

    public Game placeMove(BigInteger gameId, Move move) throws GameValidation {
        return repository.get(gameId)
                .map(logic.valid(move))
                .map(logic.play(move))
                .map(logic.setNextTurn(move))
                .flatMap(repository::save)
                .orElseThrow(InvalidGame::new);
    }
}