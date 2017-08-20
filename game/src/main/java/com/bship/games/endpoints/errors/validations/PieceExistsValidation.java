package com.bship.games.endpoints.errors.validations;

import com.bship.games.logic.definitions.PieceType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.bship.games.logic.definitions.PieceType.Dummy.INVALID_PIECE;


public class PieceExistsValidation implements ConstraintValidator<PieceExists, PieceType> {
    public void initialize(PieceExists constraint) {
    }

    public boolean isValid(PieceType piece, ConstraintValidatorContext context) {
        return piece != INVALID_PIECE;
    }
}
