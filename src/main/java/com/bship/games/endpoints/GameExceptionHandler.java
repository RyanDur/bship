package com.bship.games.endpoints;

import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return badRequest().body(getErrors(
                of(result).filter(Errors::hasFieldErrors).map(Errors::getFieldErrors).map(fieldErrors),
                of(result).filter(Errors::hasGlobalErrors).map(Errors::getGlobalErrors).map(globalErrors)
        ));
    }

    @ExceptionHandler({ShipExistsCheck.class, ShipCollisionCheck.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processShipValidationError(Exception check) {
        return badRequest().body(getErrors(of(check).map(shipError).map(Arrays::asList).map(globalErrors)));
    }

    @SafeVarargs
    private final String getErrors(Optional<String>... errors) {
        return "{\"errors\": " + stream(errors).filter(Optional::isPresent).map(Optional::get).collect(toList()) + "}";
    }

    private Function<ObjectError, String> globalError = err -> "{" +
            "\"code\": \"" + err.getCode() + "\", " +
            "\"type\": \"" + err.getObjectName() + "\", " +
            "\"message\": \"" + err.getDefaultMessage() + "\"" +
            "}";

    private Function<FieldError, String> fieldError = err -> "{" +
            "\"code\": \"" + err.getCode() + "\", " +
            "\"field\": \"" + err.getField() + "\", " +
            "\"value\": \"" + err.getRejectedValue() + "\", " +
            "\"message\": \"" + err.getDefaultMessage() + "\"" +
            "}";

    private Function<Exception, ObjectError> shipError = error ->
            new ObjectError("ship", new String[]{error.getClass().getSimpleName()},
                    error.getStackTrace(), error.getMessage());

    private Function<List<ObjectError>, String> globalErrors = errors ->
            "{\"globalErrors\": " + errors.stream().map(globalError).collect(toList()) + "}";

    private Function<List<FieldError>, String> fieldErrors = errors ->
            "{\"fieldErrors\": " + errors.stream().map(fieldError).collect(toList()) + "}";
}