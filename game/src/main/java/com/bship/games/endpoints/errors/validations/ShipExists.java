package com.bship.games.endpoints.errors.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ElementType.FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ShipExistsValidation.class)
public @interface ShipExists {
    String message() default "Ship does not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
