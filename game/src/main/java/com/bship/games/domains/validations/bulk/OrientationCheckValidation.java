package com.bship.games.domains.validations.bulk;

import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;
import com.bship.games.util.Util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class OrientationCheckValidation implements ConstraintValidator<OrientationCheck, List<Piece>> {
    public void initialize(OrientationCheck constraint) {
    }

    public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
        return pieces.stream().map(Util::pointsRange).allMatch(range ->
                range.stream().map(Point::getX).allMatch(x -> x >= 0 && x < 10) &&
                        range.stream().map(Point::getY).allMatch(y -> y >= 0 && y < 10));
    }
}
