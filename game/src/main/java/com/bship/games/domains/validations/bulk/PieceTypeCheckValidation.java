package com.bship.games.domains.validations.bulk;

import com.bship.games.domains.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class PieceTypeCheckValidation implements ConstraintValidator<PieceTypeExistenceCheck, List<Piece>> {
   public void initialize(PieceTypeExistenceCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getType).allMatch(Objects::nonNull);
   }
}
