package com.bship.games.endpoints.cabinet.repositories

import com.bship.DBHelper
import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Game
import com.bship.games.logic.definitions.GameRules
import com.nhaarman.mockito_kotlin.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.util.*
import java.util.Arrays.asList

class GameRepositoryTest {

    private lateinit var repository: GameRepository
    private lateinit var boardRepository: BoardRepository

    @Before
    fun setup() {
        val template = NamedParameterJdbcTemplate(DBHelper.reset())
        boardRepository = mock()
        repository = GameRepository(template, boardRepository)
    }

    @Test
    fun create_shouldCreateAGame() {
        val game = repository.create(GameRules.BATTLESHIP)

        assertThat(game, `is`(instanceOf(Game::class.java)))
    }

    @Test
    fun create_shouldHaveTwoBoards() {
        val game = repository.create(GameRules.BATTLESHIP)
        whenever(boardRepository.create(any(), any()))
                .thenReturn(Board.build {})
        assertThat(game.boards.size, `is`(2))
    }

    @Test
    fun create_shouldPersistGames() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        val game1 = repository.create(GameRules.BATTLESHIP)
        val game2 = repository.create(GameRules.BATTLESHIP)

        val games = repository.all

        assertThat(games, `is`(not(empty<Game>())))
        assertThat(games.size, `is`(2))
        assertThat(game1.id, `is`(equalTo(1L)))
        assertThat(game2.id, `is`(equalTo(2L)))
    }

    @Test
    fun getAll_shouldGetAllTheGames() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        val game1 = repository.create(GameRules.BATTLESHIP)
        val game2 = repository.create(GameRules.BATTLESHIP)

        val games = repository.all

        assertThat(games, containsInAnyOrder(game1, game2))
    }

    @Test
    fun getAll_shouldBeEmptyIfNoGames() {
        val games = repository.all

        assertThat(games, `is`(empty()))
    }

    @Test
    fun get_shouldRetrieveABoard() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        val createdGame = repository.create(GameRules.BATTLESHIP)
        val game = repository.get(createdGame.id)

        assertThat(createdGame, `is`(equalTo(game.orElse(null))))
    }

    @Test
    fun get_shouldOnlyRetrieveTheBoardForTheGivenId() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        repository.create(GameRules.BATTLESHIP)
        val createdGame = repository.create(GameRules.BATTLESHIP)
        repository.create(GameRules.BATTLESHIP)
        val game = repository.get(createdGame.id)

        assertThat(createdGame, `is`(equalTo(game.orElse(null))))
    }

    @Test
    fun get_shouldReturnEmptyIfTheBoardDoesNotExit() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        repository.create(GameRules.BATTLESHIP)
        repository.create(GameRules.BATTLESHIP)
        repository.create(GameRules.BATTLESHIP)
        val game = repository.get(100L)

        assertThat(game, `is`<Optional<out Any>>(Optional.empty()))
    }

    @Test
    fun save_shouldSaveTheGame() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        val game = repository.create(GameRules.BATTLESHIP).copy().withTurn(2L).build()

        repository.save(game)
        val savedGame = repository.get(game.id)

        assertThat(savedGame.orElse(null), `is`(equalTo(game)))
    }

    @Test
    fun save_shouldReturnTheSavedGame() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        val game = repository.create(GameRules.BATTLESHIP).copy().withTurn(2L).build()

        val savedGame = repository.save(game)

        assertThat(savedGame.orElse(null), `is`(equalTo(game)))
    }

    @Test
    fun save_shouldSaveTheBoards() {
        whenever(boardRepository.getAll(any())).thenReturn(asList<Board>(null, null))
        val game = repository.create(GameRules.BATTLESHIP)

        repository.save(game)

        verify(boardRepository, times(2)).save(any())
    }

    @Test
    fun delete_shouldDeleteTheGameFromTheRepository() {
        val game = repository.create(GameRules.BATTLESHIP)
        val gotGame = repository.get(game.id)
        assertThat(gotGame.isPresent, `is`(true))
        repository.delete(game)
        val gotDeletedGame = repository.get(game.id)
        assertThat(gotDeletedGame.isPresent, `is`(false))
    }
}