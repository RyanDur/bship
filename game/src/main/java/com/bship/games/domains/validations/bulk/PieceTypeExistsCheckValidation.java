package com.bship.games.domains.validations.bulk;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PieceTypeExistsCheckValidation implements ConstraintValidator<PieceTypeCheck, List<Piece>> {
   public void initialize(PieceTypeCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getType).noneMatch(Harbor.INVALID_SHIP::equals);
   }
}
