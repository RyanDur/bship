package com.bship.games;

import com.bship.games.models.Board;
import com.bship.games.models.Game;
import com.bship.games.models.Ship;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private BoardRepository boardRepository;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(BoardRepository boardRepository, GameRepository gameRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public Game getNewGame() {
        Game game = gameRepository.createGame();
        List<Board> boards = boardRepository.createBoards(game);
        return game.copy().withBoards(boards).build();
    }

    public void placeShip(int gameId, int boardId, Ship battleShipToBeCreated) {

    }
}
