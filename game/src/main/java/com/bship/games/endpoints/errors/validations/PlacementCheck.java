package com.bship.games.endpoints.errors.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ElementType.TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlacementCheckValidation.class)
public @interface PlacementCheck {
    String message() default "Incorrect ship placement.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
