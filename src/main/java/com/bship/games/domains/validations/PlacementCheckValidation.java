package com.bship.games.domains.validations;

import com.bship.games.domains.Ship;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.Math.abs;

public class PlacementCheckValidation implements ConstraintValidator<PlacementCheck, Ship> {
    public void initialize(PlacementCheck constraint) {
    }

    public boolean isValid(Ship ship, ConstraintValidatorContext context) {
        Integer startX = ship.getStart().getX();
        Integer startY = ship.getStart().getY();
        Integer endX = ship.getEnd().getX();
        Integer endY = ship.getEnd().getY();
        Integer shipSize = ship.getShipType().getSize();

        return (startX.equals(endX)) && (abs(startY - endY) + 1) == shipSize
                || (startY.equals(endY)) && (abs(startX - endX) + 1) == shipSize;
    }
}