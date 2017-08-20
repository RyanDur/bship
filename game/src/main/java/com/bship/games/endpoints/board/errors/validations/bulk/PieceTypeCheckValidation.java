package com.bship.games.endpoints.board.errors.validations.bulk;

import com.bship.games.endpoints.cabinet.entity.Piece;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

import static com.bship.games.logic.definitions.PieceType.Dummy.INVALID_PIECE;

public class PieceTypeCheckValidation implements ConstraintValidator<PieceTypeCheck, List<Piece>> {
   public void initialize(PieceTypeCheck constraint) {
   }

   public boolean isValid(List<Piece> pieces, ConstraintValidatorContext context) {
      return pieces.stream().map(Piece::getType).noneMatch(obj -> Objects.isNull(obj) || obj == INVALID_PIECE);
   }
}
