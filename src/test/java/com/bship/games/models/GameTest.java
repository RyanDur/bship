package com.bship.games.models;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

public class GameTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Game game;

    @Before
    public void setup() {
        List<Board> boards = Arrays.asList(Board.builder().build(), Board.builder().build());
        game = Game.builder().withId(19L).withBoards(boards).build();
    }

    @Test
    public void copy_shouldCopyTheGame() {
        Game gameCopy = game.copy().build();

        assertThat(game, is(equalTo(gameCopy)));
        assertThat(gameCopy, is(not(sameInstance(game))));
    }

    @Test
    public void boardsShouldBeAnImmutableList() {
        thrown.expect(UnsupportedOperationException.class);

        game.getBoards().add(Board.builder().build());
    }
}