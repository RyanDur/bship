package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import com.bship.games.domains.Move;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.repositories.BoardRepository;
import com.bship.games.repositories.GameRepository;
import com.bship.games.repositories.MoveRepository;
import com.bship.games.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bship.games.util.Util.detectCollision;
import static com.bship.games.util.Util.pointsRange;

@Service
public class GameService {
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;
    private final MoveRepository moveRepository;

    @Autowired
    public GameService(BoardRepository boardRepository,
                       GameRepository gameRepository,
                       ShipRepository shipRepository,
                       MoveRepository moveRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
        this.moveRepository = moveRepository;
    }

    public Game getNewGame() {
        Game game = gameRepository.createGame();
        List<Board> boards = boardRepository.create(game);
        return game.copy().withBoards(boards).build();
    }

    public Board placeShip(Long boardId, Ship ship) throws ShipExistsCheck, ShipCollisionCheck {
        Board board = boardRepository.get(boardId);
        if (shipExists(board.getShips(), ship)) throw new ShipExistsCheck();
        if (collision(board.getShips(), ship)) throw new ShipCollisionCheck();

        Ship createdShip = shipRepository.create(ship, boardId);

        return board.copy().addShip(createdShip).build();
    }

    public Move placeMove(Long gameId, Long boardId, Point point) {
        return moveRepository.create(boardId, point);
    }

    private boolean shipExists(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip -> savedShip.getType().equals(ship.getType()));
    }

    private boolean collision(List<Ship> ships, Ship ship) {
        return ships.stream().anyMatch(savedShip -> detectCollision(
                pointsRange(savedShip.getStart(), savedShip.getEnd()),
                pointsRange(ship.getStart(), ship.getEnd())
        ));
    }

}
