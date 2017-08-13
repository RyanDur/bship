package com.bship.games.domains.validations.bulk;

import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class PlacementOfYCheckValidation implements ConstraintValidator<PlacementOfYCheck, List<Piece>> {
   public void initialize(PlacementOfYCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream()
              .map(Piece::getPlacement)
              .map(Point::getY)
              .filter(Objects::nonNull)
              .allMatch(y -> y >= 0 && y < 10);
   }
}
