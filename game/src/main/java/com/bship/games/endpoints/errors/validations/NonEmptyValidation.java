package com.bship.games.endpoints.errors.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NonEmptyValidation implements ConstraintValidator<NonEmpty, Object> {
    public void initialize(NonEmpty constraint) {
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return obj != null && !obj.toString().trim().equals("");
    }
}
