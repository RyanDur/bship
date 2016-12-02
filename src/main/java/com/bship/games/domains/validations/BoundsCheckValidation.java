package com.bship.games.domains.validations;

import com.bship.games.domains.Point;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.bship.games.util.Util.SIDE;

public class BoundsCheckValidation implements ConstraintValidator<BoundsCheck, Point> {
    public void initialize(BoundsCheck constraint) {
    }

    public boolean isValid(Point point, ConstraintValidatorContext context) {
        Integer x = point.getX();
        Integer y = point.getY();
        return x >= 0 && y >= 0 && x < SIDE && y < SIDE;
    }
}
