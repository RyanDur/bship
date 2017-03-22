package com.bship.games.domains.validations;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Piece;
import com.bship.games.util.Util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.function.Predicate;

import static com.bship.games.domains.Direction.NONE;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class PlacementCheckValidation implements ConstraintValidator<PlacementCheck, Piece> {
    public void initialize(PlacementCheck constraint) {
    }

    public boolean isValid(Piece piece, ConstraintValidatorContext context) {
        return ofNullable(piece)
                .filter(Util::isPlaced)
                .filter(p -> nonNull(p.getType()))
                .filter(validOrientation)
                .filter(p -> Harbor.getShips().contains(p.getType()))
                .filter(p -> Harbor.valueOf(p.getType().name()).getSize().equals(p.getSize()))
                .filter(p -> Objects.equals(p.getSize(), pointsRange(p).size()))
                .filter(Util::validRange)
                .isPresent();
    }

    private Predicate<Piece> validOrientation = piece ->
            nonNull(piece.getSize()) &&
                    nonNull(piece.getOrientation()) &&
                    !piece.getOrientation().equals(NONE);
}