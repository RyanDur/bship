package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.BoardExistence;
import com.bship.games.exceptions.BoardValidation;
import com.bship.games.logic.GameLogic;
import com.bship.games.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BoardService {

    private final BoardRepository boards;
    private final GameLogic logic;

    @Autowired
    public BoardService(BoardRepository boards, GameLogic logic) {
        this.boards = boards;
        this.logic = logic;
    }

    public Board placeShip(BigInteger boardId, Ship ship) throws BoardValidation {
        return boards.get(boardId)
                .map(logic.placementCheck(ship))
                .map(board -> board.copy()
                        .withShips(otherShips(board, ship))
                        .addShip(ship.copy().withBoardId(boardId).build())
                        .build())
                .flatMap(boards::save)
                .orElseThrow(BoardExistence::new);
    }

    private List<Ship> otherShips(Board board, Ship ship) {
        return board.getShips().stream()
                .filter(o -> !o.getType().equals(ship.getType()))
                .collect(toList());
    }
}
