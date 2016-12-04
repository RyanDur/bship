package com.bship.games.endpoints;

import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.util.TriFunction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String fieldErrors = result.hasFieldErrors() ? collectFieldErrors(result) : null;
        String globalErrors = result.hasGlobalErrors() ? collectGlobalErrors(result) : null;

        return ResponseEntity.badRequest().body(getErrors(fieldErrors, globalErrors));
    }

    @ExceptionHandler({ShipExistsCheck.class, ShipCollisionCheck.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processShipExistenceCheck(Exception check) {
        return ResponseEntity.badRequest().body(getErrors(getGlobalErrors(
                singletonList(objectErrors.apply(
                        objectError.apply("ship", check.getClass().getSimpleName(), check.getMessage())))
                        .toString()
        )));
    }

    private String getErrors(String... errors) {
        return "{\"errors\": " +
                Arrays.stream(errors).filter(Objects::nonNull).collect(toList()) +
                "}";
    }

    private String getGlobalErrors(String... errors) {
        return "{\"globalErrors\": " +
                Arrays.stream(errors).filter(Objects::nonNull).collect(joining(",")) +
                "}";
    }

    private String getFieldErrors(String... errors) {
        return "{\"fieldErrors\": " +
                Arrays.stream(errors).filter(Objects::nonNull).collect(joining(",")) +
                "}";
    }

    private String collectFieldErrors(BindingResult result) {
        return getFieldErrors(
                result.getFieldErrors().stream().map(this.fieldErrors).collect(toList()).toString());
    }

    private String collectGlobalErrors(BindingResult result) {
        return getGlobalErrors(
                result.getGlobalErrors().stream().map(objectErrors).collect(toList()).toString());
    }

    private Function<ObjectError, String> objectErrors = err -> "{" +
            "\"code\": \"" + err.getCode() + "\", " +
            "\"type\": \"" + err.getObjectName() + "\", " +
            "\"message\": \"" + err.getDefaultMessage() + "\"" +
            "}";

    private Function<FieldError, String> fieldErrors = err -> "{" +
            "\"code\": \"" + err.getCode() + "\", " +
            "\"field\": \"" + err.getField() + "\", " +
            "\"value\": \"" + err.getRejectedValue() + "\", " +
            "\"message\": \"" + err.getDefaultMessage() + "\"" +
            "}";

    private TriFunction<String, String, String, ObjectError> objectError = (name, code, message) ->
            new ObjectError(name, new String[]{code}, null, message);
}
