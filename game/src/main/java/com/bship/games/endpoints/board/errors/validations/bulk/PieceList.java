package com.bship.games.endpoints.board.errors.validations.bulk;

import com.bship.games.endpoints.cabinet.entity.Piece;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.GroupSequence;
import java.util.List;

@GroupSequence({
        PieceList.class,
        ExistenceCheck.class,
        CoordinateCheck.class,
        PiecesCheck.class})
public class PieceList {

    @OrientationExistenceCheck(groups = ExistenceCheck.class)
    @PlacementExistenceCheck(groups = ExistenceCheck.class)
    @IdExistenceCheck(groups = ExistenceCheck.class)
    @PlacementOfYCheck(groups = CoordinateCheck.class)
    @PlacementOfXCheck(groups = CoordinateCheck.class)
    @OrientationCheck(groups = PiecesCheck.class)
    @PieceTypeCheck(groups = PiecesCheck.class)
    private List<Piece> pieces;

    @JsonCreator
    public PieceList(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

}
class ExistenceCheck {}
class PiecesCheck {}
class CoordinateCheck {}

