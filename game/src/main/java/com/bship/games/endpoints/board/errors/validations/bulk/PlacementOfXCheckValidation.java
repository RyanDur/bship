package com.bship.games.endpoints.board.errors.validations.bulk;

import com.bship.games.endpoints.cabinet.entity.Piece;
import com.bship.games.endpoints.cabinet.entity.Point;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class PlacementOfXCheckValidation implements ConstraintValidator<PlacementOfXCheck, List<Piece>> {
   public void initialize(PlacementOfXCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream()
              .map(Piece::getPlacement)
              .map(Point::getX)
              .filter(Objects::nonNull)
              .allMatch(x -> x >= 0 && x < 10);
   }
}
