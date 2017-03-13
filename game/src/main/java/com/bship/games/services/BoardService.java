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

    private final BoardRepository ships;
    private final GameLogic logic;

    @Autowired
    public BoardService(BoardRepository ships, GameLogic logic) {
        this.ships = ships;
        this.logic = logic;
    }

    public Optional<Board> placeShip(BigInteger boardId, Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        Board board = ships.get(boardId).filter(b -> !logic.exists(b, ship))
                .orElseThrow(ShipExistsCheck::new);
        if (logic.collision(board, ship)) throw new ShipCollisionCheck();

        return ships.save(board.copy().addShip(ship).build());
    }
}
