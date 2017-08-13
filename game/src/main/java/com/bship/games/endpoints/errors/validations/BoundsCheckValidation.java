package com.bship.games.endpoints.errors.validations;

import com.bship.games.endpoints.cabinet.entity.Point;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Optional;

import static com.bship.games.util.Util.SIDE;

public class BoundsCheckValidation implements ConstraintValidator<BoundsCheck, Point> {
    public void initialize(BoundsCheck constraint) {
    }

    public boolean isValid(Point point, ConstraintValidatorContext context) {
        return Optional.ofNullable(point).map(p -> {
            Integer x = p.getX();
            Integer y = p.getY();
            return x != null && y != null && x >= 0 && y >= 0 && x < SIDE && y < SIDE;
        }).orElse(true);
    }
}
