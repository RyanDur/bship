package com.bship.games.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
public class ShipExceptionHandler implements BShipExceptionHandler {

    private Function<Exception, ObjectError> shipError;

    public ShipExceptionHandler() {
        shipError = error.apply("ship");
    }

    @Override
    @ExceptionHandler({ShipExistsCheck.class, ShipCollisionCheck.class})
    public ResponseEntity processValidationError(Exception check) {
        return badRequest().body(getErrors(of(check).map(shipError).map(Stream::of).map(objectErrors)));
    }
}