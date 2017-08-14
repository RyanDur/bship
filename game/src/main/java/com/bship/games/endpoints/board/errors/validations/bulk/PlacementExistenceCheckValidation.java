package com.bship.games.endpoints.board.errors.validations.bulk;

import com.bship.games.endpoints.cabinet.entity.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class PlacementExistenceCheckValidation implements ConstraintValidator<PlacementExistenceCheck, List<Piece>> {
   public void initialize(PlacementExistenceCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getPlacement).allMatch(Objects::nonNull);
   }
}
