package com.bship.games.endpoints;

import com.bship.games.endpoints.RequestErrors.FieldValidation;
import com.bship.games.endpoints.RequestErrors.GameErrors;
import com.bship.games.endpoints.RequestErrors.ObjectValidation;
import com.bship.games.endpoints.RequestErrors.ValidationFieldError;
import com.bship.games.endpoints.RequestErrors.ValidationObjectError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.badRequest;

public interface BadRequestHandler {

    ResponseEntity processValidationError(Exception check);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    default ResponseEntity processArgumentError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return badRequest().body(getErrors(
                of(result).filter(Errors::hasFieldErrors).map(Errors::getFieldErrors).map(Collection::stream).map(fieldErrors),
                of(result).filter(Errors::hasGlobalErrors).map(Errors::getGlobalErrors).map(Collection::stream).map(objectErrors)
        ));
    }

    default GameErrors getErrors(Optional... errors) {
        return GameErrors.builder().withErrors(stream(errors).filter(Optional::isPresent)
                .map(Optional::get).collect(toList())).build();
    }

    Function<ObjectError, ValidationObjectError> objectError = err ->
            ValidationObjectError.builder().withError(err).build();

    Function<FieldError, ValidationFieldError> fieldError = err ->
            ValidationFieldError.builder().withError(err).build();

    Function<String, Function<Exception, ObjectError>> error = name -> error ->
            new ObjectError(name, new String[]{error.getClass().getSimpleName()},
                    error.getStackTrace(), error.getMessage());

    Function<Stream<ObjectError>, ObjectValidation> objectErrors = errors ->
            ObjectValidation.builder().withErrors(errors.map(objectError).collect(toList())).build();

    Function<Stream<FieldError>, FieldValidation> fieldErrors = errors ->
            FieldValidation.builder().withErrors(errors.map(fieldError).collect(toList())).build();
}
