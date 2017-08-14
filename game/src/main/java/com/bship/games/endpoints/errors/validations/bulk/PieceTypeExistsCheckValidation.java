package com.bship.games.endpoints.errors.validations.bulk;

import com.bship.games.endpoints.cabinet.entity.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static com.bship.games.logic.PieceType.Dummy.INVALID_PIECE;


public class PieceTypeExistsCheckValidation implements ConstraintValidator<PieceTypeCheck, List<Piece>> {
   public void initialize(PieceTypeCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getType).noneMatch(INVALID_PIECE::equals);
   }
}
