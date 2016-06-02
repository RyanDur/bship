package com.bship.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private BoardRepository boardRepository;

    @Autowired
    public GameService(BoardRepository boardRepository) {

        this.boardRepository = boardRepository;
    }

    public Game getNewGame() {
        List<Board> boards = boardRepository.createBoards();
        return new Game(boards);
    }
}
