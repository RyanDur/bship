package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository repository;
    private final GameLogic logic;

    @Autowired
    public BoardService(BoardRepository repository, GameLogic logic) {
        this.repository = repository;
        this.logic = logic;
    }

    public Board placeShip(Long boardId, Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        Board board = repository.get(boardId);
        if (logic.exists(board, ship)) throw new ShipExistsCheck();
        if (logic.collision(board, ship)) throw new ShipCollisionCheck();

        return repository.save(board.copy().addShip(ship).build());
    }
}
