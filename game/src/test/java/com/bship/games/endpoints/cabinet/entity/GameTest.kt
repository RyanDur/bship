package com.bship.games.endpoints.cabinet.entity

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test
import java.util.*

class GameTest {

    private lateinit var game: Game

    @Before
    fun setup() {
        val boards = Arrays.asList(Board.build {}, Board.build {})
        game = Game.builder().withId(19L).withBoards(boards).build()
    }

    @Test
    fun copy_shouldCopyTheGame() {
        val gameCopy = game.copy()

        assertThat(game).isEqualToComparingFieldByField(gameCopy)
        assertThat(game).isNotSameAs(gameCopy)
    }

    @Test
    fun boardsShouldBeAnImmutableList() {
        assertThatThrownBy { game.boards.add(Board.build {}) }
                .isInstanceOf(UnsupportedOperationException::class.java)
    }
}