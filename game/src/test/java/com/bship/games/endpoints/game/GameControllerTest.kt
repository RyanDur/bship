package com.bship.games.endpoints.game

import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Game
import com.bship.games.endpoints.cabinet.entity.Move
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.endpoints.errors.RequestErrors.FieldValidation
import com.bship.games.endpoints.errors.RequestErrors.GameErrors
import com.bship.games.endpoints.errors.RequestErrors.ObjectValidation
import com.bship.games.endpoints.game.errors.MoveCollision
import com.bship.games.endpoints.game.errors.TurnCheck
import com.bship.games.logic.definitions.GameRules
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
class GameControllerTest {

    private lateinit var mockService: GameService
    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper
    private val game = GameRules.BATTLESHIP

    @Before
    fun setup() {
        mockService = mock()
        val gameController = GameController(mockService)
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build()
        mapper = ObjectMapper().registerKotlinModule()
    }

    @Test
    @Throws(Exception::class)
    fun createGame_shouldDefineRequestMappingForPostingToGamesEndpoint() {
        mockMvc.perform(post("/games/BATTLESHIP"))
    }

    @Test
    @Throws(Exception::class)
    fun createGame_shouldRespondWith201() {
        mockMvc.perform(post("/games/BATTLESHIP"))
                .andExpect(status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun createGame_shouldProduceJSONWithAUTF8Charset() {
        val expected = Game.builder().build()
        whenever(mockService.getNewGame(game)).thenReturn(expected)
        mockMvc.perform(post("/games/BATTLESHIP")
                .accept(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
    }

    @Test
    @Throws(Exception::class)
    fun createGame_shouldReturnAGame() {
        val expected = Game.builder().withBoards(emptyList()).build()
        whenever(mockService.getNewGame(game)).thenReturn(expected)

        val actual = mapper.readValue(mockMvc.perform(post("/games/BATTLESHIP"))
                .andReturn()
                .response
                .contentAsString, Game::class.java)

        assertThat(actual, `is`(equalTo(expected)))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldDefineRequestMappingForPuttingAMoveOntoABoardWithRespectToAGame() {
        mockMvc.perform(put("/games/1/boards/1"))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldBeAbleToPlaceAMoveOnTheBoard() {
        val point = Point(0, 0)
        val move = Move.builder().withPoint(point).build()

        whenever(mockService.placeMove(any(), any()))
                .thenReturn(Game.builder()
                        .withBoards(listOf(Board.build { withMoves { listOf(move) } })).build())

        val content = mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString

        val actual = mapper.readValue(content, Game::class.java)

        assertThat(actual.boards[0].moves, contains(move))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldNotAllowTheXToBeLessThanTheWidthOfTheBoard() {
        val point = Point(-1, 0)
        val move = Move.builder().withPoint(point).build()

        val actual = mapper.readValue(mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, `is`("out of bounds."))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldNotAllowTheXToBeGreaterThanTheHeightOfTheBoard() {
        val point = Point(10, 0)
        val move = Move.builder().withPoint(point).build()

        whenever(mockService.placeMove(any(), any()))
                .thenReturn(Game.builder()
                        .withBoards(listOf(Board.build { withMoves { listOf(move) } })).build())

        val actual = mapper.readValue(mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, `is`("out of bounds."))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldNotAllowTheYToBeLessThanTheWidthOfTheBoard() {
        val point = Point(0, -1)
        val move = Move.builder().withPoint(point).build()

        whenever(mockService.placeMove(any(), any()))
                .thenReturn(Game.builder()
                        .withBoards(listOf(Board.build { withMoves { listOf(move) } })).build())

        val actual = mapper.readValue(mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, `is`("out of bounds."))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldNotAllowTheYToBeGreaterThanTheHeightOfTheBoard() {
        val point = Point(0, 10)
        val move = Move.builder().withPoint(point).build()

        whenever(mockService.placeMove(any(), any()))
                .thenReturn(Game.builder()
                        .withBoards(listOf(Board.build { withMoves { listOf(move) } })).build())

        val actual = mapper.readValue(mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, `is`("out of bounds."))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldHandleMoveCollisions() {
        val point = Point(0, 0)
        val move = Move.builder().withPoint(point).build()

        whenever(mockService.placeMove(any(), any())).thenThrow(MoveCollision())

        val actual = mapper.readValue(mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], ObjectValidation::class.java)
        assertThat(error.validations[0].message, `is`("Move already exists on board."))
    }

    @Test
    @Throws(Exception::class)
    fun placeMove_shouldHandleTurnChecks() {
        val point = Point(0, 0)
        val move = Move.builder().withPoint(point).build()

        whenever(mockService.placeMove(any(), any())).thenThrow(TurnCheck())

        val actual = mapper.readValue(mockMvc.perform(put("/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(move.toString()))
                .andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], ObjectValidation::class.java)
        assertThat(error.validations[0].message, `is`("It is not your turn."))
    }
}