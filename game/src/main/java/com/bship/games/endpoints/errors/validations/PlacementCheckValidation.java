package com.bship.games.endpoints.errors.validations;

import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.logic.rules.Harbor;
import com.bship.games.util.Util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.function.Predicate;

import static com.bship.games.logic.rules.Direction.NONE;
import static com.bship.games.util.Util.pointsRange;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class PlacementCheckValidation implements ConstraintValidator<PlacementCheck, Piece> {
    public void initialize(PlacementCheck constraint) {
    }

    public boolean isValid(Piece piece, ConstraintValidatorContext context) {
        return validate(piece);
    }

    private boolean validate(Piece piece) {
        return ofNullable(piece)
                .filter(Util::isPlaced)
                .filter(p -> nonNull(p.getType()))
                .filter(validOrientation)
                .filter(p -> Harbor.getPieces().collect(toList()).contains(p.getType()))
                .filter(p -> Objects.equals(p.getType().getSize(), pointsRange(p).size()))
                .filter(Util::validRange)
                .isPresent();
    }

    private Predicate<Piece> validOrientation = piece ->
            nonNull(piece.getType().getSize()) &&
                    nonNull(piece.getOrientation()) &&
                    !piece.getOrientation().equals(NONE);
}