package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.exceptions.MoveCollision;
import com.bship.games.exceptions.TurnCheck;
import com.bship.games.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Optional.of;

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
        return game -> of(game)
                .map(Game::getBoards)
                .map(collectIds)
                .map(getNextTurn(boardId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(save(game))
                .map(getSavedGame);
    }

    private Function<Stream<Long>, Optional<Long>> getNextTurn(Long boardId) {
        return ids -> ids.filter(logic.nextTurn(boardId)).findFirst();
    }

    private Function<Long, Supplier<Game>> save(Game game) {
        return nextTurn -> {
            Game finishedTurn = game.copy().withTurn(nextTurn).build();
            return () -> repository.save(finishedTurn);
        };
    }

    private Function<Supplier<Game>, Game> getSavedGame = Supplier::get;
    private Function<List<Board>, Stream<Long>> collectIds = boards -> boards.stream().map(Board::getId);
}