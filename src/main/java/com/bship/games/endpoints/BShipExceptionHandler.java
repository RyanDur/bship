package com.bship.games.endpoints;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public interface BShipExceptionHandler {

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

    Function<List<ObjectError>, String> objectErrors = errors ->
            "{\"objectValidation\": " + errors.stream().map(objectError).collect(toList()) + "}";

    Function<List<FieldError>, String> fieldErrors = errors ->
            "{\"fieldValidation\": " + errors.stream().map(fieldError).collect(toList()) + "}";
}
