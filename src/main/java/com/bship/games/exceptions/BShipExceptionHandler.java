package com.bship.games.exceptions;

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

public interface BShipExceptionHandler {

    ResponseEntity processValidationError(Exception check);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    default ResponseEntity processArgumentError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return badRequest().body(getErrors(
                of(result).filter(Errors::hasFieldErrors).map(Errors::getFieldErrors)
                        .map(Collection::stream).map(fieldErrors),
                of(result).filter(Errors::hasGlobalErrors).map(Errors::getGlobalErrors)
                        .map(Collection::stream).map(objectErrors)
        ));
    }

    default String getErrors(Optional... errors) {
        return "{\"errors\": " + stream(errors).filter(Optional::isPresent).map(Optional::get).collect(toList()) + "}";
    }

    Function<ObjectError, String> objectError = err -> "{" +
            "\"code\": \"" + err.getCode() + "\", " +
            "\"type\": \"" + err.getObjectName() + "\", " +
            "\"message\": \"" + err.getDefaultMessage() + "\"" +
            "}";

    Function<FieldError, String> fieldError = err -> "{" +
            "\"code\": \"" + err.getCode() + "\", " +
            "\"field\": \"" + err.getField() + "\", " +
            "\"value\": \"" + err.getRejectedValue() + "\", " +
            "\"message\": \"" + err.getDefaultMessage() + "\"" +
            "}";

    Function<String, Function<Exception, ObjectError>> error = name -> error ->
            new ObjectError(name, new String[]{error.getClass().getSimpleName()},
                    error.getStackTrace(), error.getMessage());

    Function<Stream<ObjectError>, String> objectErrors = errors ->
            "{\"objectValidation\": " + errors.map(objectError).collect(toList()) + "}";

    Function<Stream<FieldError>, String> fieldErrors = errors ->
            "{\"fieldValidation\": " + errors.map(fieldError).collect(toList()) + "}";
}
