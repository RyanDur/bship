package com.bship.games.domains.validations.bulk;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ElementType.FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCheckValidation.class)
public @interface IdExistenceCheck {
    String message() default "An Id is missing.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
