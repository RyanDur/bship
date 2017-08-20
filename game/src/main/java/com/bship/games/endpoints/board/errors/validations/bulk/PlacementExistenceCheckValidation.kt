package com.bship.games.endpoints.board.errors.validations.bulk

import com.bship.games.endpoints.cabinet.entity.Piece
import java.util.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PlacementExistenceCheckValidation : ConstraintValidator<PlacementExistenceCheck, List<Piece>> {
    override fun initialize(constraint: PlacementExistenceCheck) {}

    override fun isValid(pieces: List<Piece>, context: ConstraintValidatorContext): Boolean {
        return pieces.map({ it.placement }).none { obj -> Objects.isNull(obj) || Objects.isNull(obj.x) && Objects.isNull(obj.y) }

    }
}
