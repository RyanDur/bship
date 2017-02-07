package com.bship.games.domains.validations;

import com.bship.games.domains.Harbor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.bship.games.domains.Harbor.INVALID_SHIP;

public class ShipExistsValidation implements ConstraintValidator<ShipExists, Harbor> {
    public void initialize(ShipExists constraint) {
    }

    public boolean isValid(Harbor shipType, ConstraintValidatorContext context) {
        return shipType != INVALID_SHIP;
    }
}
