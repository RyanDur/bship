package com.bship.games.domains.validations.bulk;

import com.bship.games.domains.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class IdCheckValidation implements ConstraintValidator<IdExistenceCheck, List<Piece>> {
   public void initialize(IdExistenceCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getId).allMatch(Objects::nonNull);
   }

}
