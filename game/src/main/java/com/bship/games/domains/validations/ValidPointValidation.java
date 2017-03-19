package com.bship.games.domains.validations;

import com.bship.games.domains.Point;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class ValidPointValidation implements ConstraintValidator<ValidPoint, Point> {
    @Override
    public void initialize(ValidPoint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Point point, ConstraintValidatorContext context) {
        return ofNullable(point)
                .filter(p -> nonNull(p.getX()))
                .filter(p -> nonNull(p.getY()))
                .isPresent();
    }
}
