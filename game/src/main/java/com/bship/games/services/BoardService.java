package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository repository;
    private final GameLogic logic;

    @Autowired
    public BoardService(BoardRepository repository, GameLogic logic) {
        this.repository = repository;
        this.logic = logic;
    }

    public Optional<Board> placeShip(BigInteger boardId, Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        Optional<Board> board = repository.get(boardId);
        if (logic.exists(board.get(), ship)) throw new ShipExistsCheck();
        if (logic.collision(board.get(), ship)) throw new ShipCollisionCheck();

        return repository.save(board.get().copy().addShip(ship).build());
    }
}
