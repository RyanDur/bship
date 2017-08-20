package com.bship.games.endpoints.board.errors.validations.bulk;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ElementType.FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PieceTypeCheckValidation.class)
public @interface PieceTypeCheck {
    String message() default "Invalid piece type.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
