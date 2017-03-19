package com.bship.games.domains.validations;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Optional;

import static java.lang.Math.abs;

public class PlacementCheckValidation implements ConstraintValidator<PlacementCheck, Piece> {
    public void initialize(PlacementCheck constraint) {
    }

    public boolean isValid(Piece piece, ConstraintValidatorContext context) {
        return Optional.ofNullable(piece).map(s -> {
            Point start = s.getStart();
            Point end = s.getEnd();
            Harbor type = s.getType();

            return type == null || start == null || end == null || type == Harbor.INVALID_SHIP ||
                    (start.getX() == end.getX()) && (abs(start.getY() - end.getY()) + 1) == type.getSize()
                    || (start.getY() == end.getY()) && (abs(start.getX() - end.getX()) + 1) == type.getSize();
        }).orElse(false);
    }
}