package com.bship.games.services;

import com.bship.games.domains.Board;
import com.bship.games.domains.Piece;
import com.bship.games.exceptions.BoardExistence;
import com.bship.games.exceptions.BoardValidation;
import com.bship.games.logic.GameLogic;
import com.bship.games.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

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

    public Board placePiece(Long boardId, Piece piece) throws BoardValidation {
        return boards.get(boardId)
                .map(logic.placementCheck(piece))
                .map(addPieceToBoard(boardId, piece))
                .flatMap(boards::save)
                .orElseThrow(BoardExistence::new);
    }

    private Function<Board, Board> addPieceToBoard(Long boardId, Piece piece) {
        return board -> board.copy()
                .withPieces(otherPieces(board, piece))
                .addPiece(piece.copy().withBoardId(boardId).build())
                .build();
    }

    private List<Piece> otherPieces(Board board, Piece piece) {
        return board.getPieces().stream()
                .filter(o -> !o.getType().equals(piece.getType()))
                .collect(toList());
    }
}
