package com.bship.games.domains;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShipTest {

    @Test
    public void isPlaced_shouldKnowIfTheBeginningAndEndIsSet() {
        Ship ship = Ship.builder()
                .withStart(new Point(1, 2))
                .withEnd(new Point(0, 0))
                .build();

        assertThat(ship.isPlaced(), is(true));
    }

    @Test
    public void isPlaced_shouldKnowIfTheBeginningAndEndIsNotSet() {
        Ship ship = Ship.builder()
                .build();

        assertThat(ship.isPlaced(), is(false));
    }

    @Test
    public void isPlaced_shouldKnowIfTheBeginningPointIsNotSet() {
        Ship ship = Ship.builder()
                .withStart(new Point())
                .withEnd(new Point(0, 0))
                .build();

        assertThat(ship.isPlaced(), is(false));
    }

    @Test
    public void isPlaced_shouldKnowIfTheEndPointIsNotSet() {
        Ship ship = Ship.builder()
                .withStart(new Point(3,4))
                .withEnd(new Point())
                .build();

        assertThat(ship.isPlaced(), is(false));
    }
}