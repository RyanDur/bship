package com.bship.games.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return ResponseEntity.badRequest().body("{\"errors\": " +
                Stream.of(collectFieldErrors(result), collectGlobalErrors(result))
                        .filter(Objects::nonNull).collect(toList()) +
                "}");
    }

    private String collectFieldErrors(BindingResult result) {
        return result.hasFieldErrors() ? "{\"fieldErrors\": " +
                result.getFieldErrors().stream().map(this.fieldErrors).collect(toList()) + "}" : null;
    }

    private String collectGlobalErrors(BindingResult result) {
        return result.hasGlobalErrors() ? "{\"globalErrors\": " +
                result.getGlobalErrors().stream().map(objectErrors).collect(toList()) + "}" : null;
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
}
