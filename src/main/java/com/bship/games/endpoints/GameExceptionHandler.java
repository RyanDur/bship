package com.bship.games.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String fieldErrors = null;
        String globalErrors = null;

        if (result.hasFieldErrors()) {
            fieldErrors = "{\"fieldErrors\": " +
                    result.getFieldErrors().stream().map(err -> "{" +
                            "\"code\": \"" + err.getCode() + "\", " +
                            "\"field\": \"" + err.getField() + "\", " +
                            "\"value\": \"" + err.getRejectedValue() + "\", " +
                            "\"message\": \"" + err.getDefaultMessage() + "\"" +
                            "}").collect(toList()) + "}";
        }

        if (result.hasGlobalErrors()) {
            globalErrors = "{\"globalErrors\": " +
                    result.getGlobalErrors().stream().map(err -> "{" +
                            "\"code\": \"" + err.getCode() + "\", " +
                            "\"type\": \"" + err.getObjectName() + "\", " +
                            "\"message\": \"" + err.getDefaultMessage() + "\"" +
                            "}").collect(toList()) + "}";
        }

        return ResponseEntity.badRequest().body("{\"errors\": " +
                Stream.of(fieldErrors, globalErrors).filter(Objects::nonNull).collect(toList()) +
                "}");
    }
}
