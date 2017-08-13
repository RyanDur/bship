package com.bship.games.endpoints.errors.validations;

import com.bship.games.logic.rules.Harbor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.bship.games.logic.rules.Harbor.INVALID_SHIP;

public class ShipExistsValidation implements ConstraintValidator<ShipExists, Harbor> {
    public void initialize(ShipExists constraint) {
    }

    public boolean isValid(Harbor shipType, ConstraintValidatorContext context) {
        return shipType != INVALID_SHIP;
    }
}
