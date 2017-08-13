package com.bship.games.endpoints.errors.validations.bulk;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ElementType.FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrientationExistenceCheckValidation.class)
public @interface OrientationExistenceCheck {
    String message() default "Missing orientation.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
