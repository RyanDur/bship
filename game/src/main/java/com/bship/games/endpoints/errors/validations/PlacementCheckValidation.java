package com.bship.games.endpoints.errors.validations;

import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.logic.definitions.Harbor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static com.bship.games.util.Util.Companion;
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
                .filter(Companion::isPlaced)
                .filter(p -> Harbor.getPieces().collect(toList()).contains(p.getType()))
                .filter(p -> Objects.equals(p.getType().getSize(), Companion.pointsRange(p).size()))
                .filter(Companion::validRange)
                .isPresent();
    }
}