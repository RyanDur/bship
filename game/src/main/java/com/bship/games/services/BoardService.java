package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.BoardValidation;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boards;
    private final GameLogic logic;

    @Autowired
    public BoardService(BoardRepository boards, GameLogic logic) {
        this.boards = boards;
        this.logic = logic;
    }

    public Optional<Board> placeShip(BigInteger boardId, Ship ship) throws BoardValidation {
        Board board = boards.get(boardId).filter(b -> !logic.exists(b, ship))
                .orElseThrow(ShipExistsCheck::new);
        if (logic.collision(board, ship)) throw new ShipCollisionCheck();

        return boards.save(ship, boardId);
    }
}
