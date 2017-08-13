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
import java.util.stream.Collectors;

import static com.bship.games.util.Util.concat;

@Service
public class BoardService {

    private final BoardRepository boards;
    private final GameLogic logic;

    @Autowired
    public BoardService(BoardRepository boards, GameLogic logic) {
        this.boards = boards;
        this.logic = logic;
    }

    public Board placePiece(Long boardId, List<Piece> pieces) throws BoardValidation {
        return boards.get(boardId)
                .map(logic.placementCheck(pieces))
                .map(addPiecesToBoard(pieces))
                .flatMap(boards::save)
                .orElseThrow(BoardExistence::new);
    }

    private Function<Board, Board> addPiecesToBoard(List<Piece> pieces) {
        return board -> board.copy()
                .withPieces(concat(set(pieces, board), pieces))
                .build();
    }

    private List<Piece> set(List<Piece> pieces, Board board) {
        return board.getPieces().stream().filter(piece ->
                pieces.stream().noneMatch(p -> p.getType() == piece.getType()))
                .collect(Collectors.toList());
    }
}
