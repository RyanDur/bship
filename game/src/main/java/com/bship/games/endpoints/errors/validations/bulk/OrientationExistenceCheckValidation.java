package com.bship.games.endpoints.errors.validations.bulk;

import com.bship.games.endpoints.cabinet.entity.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class OrientationExistenceCheckValidation implements ConstraintValidator<OrientationExistenceCheck, List<Piece>> {
   public void initialize(OrientationExistenceCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getOrientation).allMatch(Objects::nonNull);
   }
}
