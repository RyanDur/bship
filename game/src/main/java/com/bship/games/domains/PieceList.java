package com.bship.games.domains;

import com.bship.games.domains.validations.bulk.IdExistenceCheck;
import com.bship.games.domains.validations.bulk.OrientationCheck;
import com.bship.games.domains.validations.bulk.OrientationExistenceCheck;
import com.bship.games.domains.validations.bulk.PieceTypeCheck;
import com.bship.games.domains.validations.bulk.PieceTypeExistenceCheck;
import com.bship.games.domains.validations.bulk.PlacementExistenceCheck;
import com.bship.games.domains.validations.bulk.PlacementOfXCheck;
import com.bship.games.domains.validations.bulk.PlacementOfYCheck;
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
    @PieceTypeExistenceCheck(groups = ExistenceCheck.class)
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

