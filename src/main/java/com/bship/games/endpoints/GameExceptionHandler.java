package com.bship.games.endpoints;

import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.function.Function;

import static java.util.Optional.of;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
public class GameExceptionHandler implements BShipExceptionHandler {

    private Function<Exception, ObjectError> shipError;

    public GameExceptionHandler() {
        shipError = error.apply("ship");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return badRequest().body(getErrors(
                of(result).filter(Errors::hasFieldErrors).map(Errors::getFieldErrors).map(fieldErrors),
                of(result).filter(Errors::hasGlobalErrors).map(Errors::getGlobalErrors).map(objectErrors)
        ));
    }

    @ExceptionHandler({ShipExistsCheck.class, ShipCollisionCheck.class})
    public ResponseEntity processShipValidationError(Exception check) {
        return badRequest().body(getErrors(of(check).map(shipError).map(Arrays::asList).map(objectErrors)));
    }
}