package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Ship;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;

    @Autowired
    public GameService(BoardRepository boardRepository,
                       GameRepository gameRepository,
                       ShipRepository shipRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
    }

    public Game getNewGame() {
        Game game = gameRepository.createGame();
        List<Board> boards = boardRepository.create(game);
        return game.copy().withBoards(boards).build();
    }

    public Board placeShip(Long boardId, Ship ship) {
        Board board = boardRepository.get(boardId);
        Ship createdShip = shipRepository.create(ship, boardId);
        return board.copy().addShip(createdShip).build();
    }
}
