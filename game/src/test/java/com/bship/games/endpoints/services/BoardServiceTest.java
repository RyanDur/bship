package com.bship.games.endpoints.services;

import com.bship.games.endpoints.board.BoardService;
import com.bship.games.endpoints.cabinet.entity.Board;
import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;
import com.bship.games.endpoints.cabinet.repositories.BoardRepository;
import com.bship.games.endpoints.errors.exceptions.BoardExistence;
import com.bship.games.endpoints.errors.exceptions.BoardValidation;
import com.bship.games.endpoints.errors.exceptions.ShipCollisionCheck;
import com.bship.games.logic.GameLogic;
import com.bship.games.logic.rules.Harbor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Optional;

import static com.bship.games.logic.rules.Direction.NONE;
import static com.bship.games.logic.rules.Direction.RIGHT;
import static com.bship.games.logic.rules.Harbor.AIRCRAFT_CARRIER;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private BoardService service;
    private GameLogic logic;
    private BoardRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(BoardRepository.class);
        logic = mock(GameLogic.class);
        service = new BoardService(repository, logic);
    }

    @Test
    public void placeShip_shouldPlaceAShipOnTheBoard() throws BoardValidation {
        long boardId = 1L;
        Board board = Board.builder().withId(boardId).withPieces(getShips()).build();
        Piece piece = Piece.builder()
                .withPlacement(new Point(3,2))
                .withOrientation(RIGHT)
                .withBoardId(boardId)
                .withType(AIRCRAFT_CARRIER)
                .build();

        List<Piece> unplaced = board.getPieces().stream()
                .filter(o -> !o.getType().equals(piece.getType()))
                .collect(toList());

        Board expected = board.copy().withPieces(unplaced).addPiece(piece).build();

        when(repository.get(boardId)).thenReturn(of(board));
        when(logic.placementCheck(singletonList(piece))).thenReturn(b -> b);
        when(repository.save(any(Board.class))).thenReturn(of(expected));

        Board actual = service.placePiece(boardId, singletonList(piece));

        verify(repository).save(expected);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void placeShip_shouldThrowABoardExistenceIfThereIsNoBoard() throws BoardValidation {
        thrown.expect(BoardExistence.class);
        thrown.expectMessage("Board Does Not Exist!");

        long boardId = 1L;
        Board board = Board.builder().build();
        Piece piece = Piece.builder().build();
        Board expected = board.copy().addPiece(piece).build();

        when(repository.get(boardId)).thenReturn(Optional.empty());
        when(logic.placementCheck(singletonList(piece))).thenReturn(b -> b);
        when(repository.save(expected)).thenReturn(of(expected));

        service.placePiece(boardId, singletonList(piece));
    }

    @Test
    public void placeShip_shouldThrowABoardExistenceIfThereIsNoBoardAfterSaving() throws BoardValidation {
        thrown.expect(BoardExistence.class);
        thrown.expectMessage("Board Does Not Exist!");

        long boardId = 1L;
        Board board = Board.builder().withPieces(emptyList()).build();
        Piece piece = Piece.builder().build();

        when(repository.get(boardId)).thenReturn(of(board));
        when(logic.placementCheck(singletonList(piece))).thenReturn(b -> b);
        when(repository.save(any(Board.class))).thenReturn(Optional.empty());

        service.placePiece(boardId, singletonList(piece));
    }

    @Test
    public void placeShip_shouldThrowABoardValidationIfThereIsNoBoardAfterCheck() throws BoardValidation {
        thrown.expect(BoardValidation.class);

        long boardId = 1L;
        Board board = Board.builder().build();
        Piece piece = Piece.builder().build();
        Board expected = board.copy().addPiece(piece).build();

        when(repository.get(boardId)).thenReturn(of(board));
        doThrow(new ShipCollisionCheck()).when(logic).placementCheck(singletonList(piece));
        when(repository.save(expected)).thenReturn(Optional.empty());

        service.placePiece(boardId, singletonList(piece));
    }

    private List<Piece> getShips() {
        return Harbor.getPieces().map(ship -> Piece.builder()
                .withType(ship)
                .withPlacement(new Point())
                .withOrientation(NONE)
                .withBoardId(1L)
                .build()).collect(toList());
    }
}