package com.bship.games.endpoints.board

import com.bship.games.endpoints.board.errors.exceptions.ShipCollisionCheck
import com.bship.games.endpoints.board.errors.exceptions.ShipExistsCheck
import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Piece
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.endpoints.errors.RequestErrors.FieldValidation
import com.bship.games.endpoints.errors.RequestErrors.GameErrors
import com.bship.games.endpoints.errors.RequestErrors.ObjectValidation
import com.bship.games.logic.definitions.Direction.*
import com.bship.games.logic.definitions.Harbor.BATTLESHIP
import com.bship.games.logic.definitions.Harbor.DESTROYER
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.anyListOf
import org.mockito.Matchers.anyLong
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class BoardsControllerTest {

    private lateinit var mockService: BoardService
    private lateinit var mockMvc: MockMvc
    private lateinit var mapper: ObjectMapper

    @Before
    fun setup() {
        mockService = mock(BoardService::class.java)
        val boardsController = BoardsController(mockService)
        mockMvc = MockMvcBuilders.standaloneSetup(boardsController).build()
        mapper = ObjectMapper()
    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveARoute() {
        mockMvc.perform(put("/boards/9"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldProduceJSONWithCharsetUTF8() {
        `when`(mockService.placePiece(anyLong(), anyListOf(Piece::class.java)))
                .thenReturn(Board.build {})
        val pieces = listOf(Piece.build {
            withType { BATTLESHIP }
            withId { 1 }
            withPlacement { Point(4, 5) }
            withOrientation { LEFT }
        })

        mockMvc.perform(put("/boards/9").contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRespondWith200() {
        `when`(mockService.placePiece(anyLong(), anyListOf(Piece::class.java)))
                .thenReturn(Board.build {})

        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        })

        mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString()))
                .andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    fun shouldRequireATypeOfShip() {
        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "  \"id\": 1," +
                        "  \"size\": 2," +
                        "  \"orientation\": \"DOWN\"," +
                        "  \"placement\": {" +
                        "    \"x\": 1," +
                        "    \"y\": 5" +
                        "  }" +
                        "}]"
                )).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val messages = getFieldErrorMessages(actual)
        assertThat(messages.contains("Invalid piece type."), Matchers.`is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRequireAnExistingTypeOfShip() {
        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "    \"id\": 1," +
                        "    \"type\": \"GAS_STATION\"," +
                        "    \"orientation\": \"DOWN\"," +
                        "    \"placement\": {" +
                        "      \"x\": 0," +
                        "      \"y\": 0" +
                        "    }" +
                        "}]"
                )).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val messages = getFieldErrorMessages(actual)

        assertThat(messages.contains("Invalid piece type."), Matchers.`is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRequireAPlacementPointForAShip() {
        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "  \"id\": 1," +
                        "  \"type\": \"DESTROYER\"," +
                        "  \"orientation\": \"DOWN\"" +
                        "}]"
                )).andReturn()
                .response
                .contentAsString, GameErrors::class.java)
        val messages = getFieldErrorMessages(actual)
        assertThat(messages.contains("Missing placement."), Matchers.`is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRequireAnOrientationForAShip() {
        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{" +
                        "  \"id\": 1," +
                        "  \"type\": \"AIRCRAFT_CARRIER\"," +
                        "  \"placement\": {" +
                        "    \"x\": 0," +
                        "    \"y\": 4" +
                        "  }" +
                        "}]"
                )).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Missing orientation."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowTheStartXToBeLessThanTheWidthOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(-1, 0) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val messages = getFieldErrorMessages(actual)
        assertThat(messages.contains("Incorrect placement of X axis."), Matchers.`is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowTheStartXToBeGreaterThanTheWidthOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(11, 0) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect placement of X axis."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowTheStartYToBeLessThanTheHeightOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, -1) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect placement of Y axis."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowTheStartYToBeGreaterThanTheHeightOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 11) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect placement of Y axis."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowThePieceToGoOffTheTopOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 0) }
            withOrientation { UP }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect orientation."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowThePieceToGoOffTheBottomOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 9) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect orientation."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowThePieceToGoOffTheLeftOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 5) }
            withOrientation { LEFT }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect orientation."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotAllowThePieceToGoOffTheRightOfTheBoard() {
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(9, 5) }
            withOrientation { RIGHT }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Incorrect orientation."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldHandleShipExistence() {
        doThrow(ShipExistsCheck()).`when`<BoardService>(mockService).placePiece(anyLong(), anyListOf(Piece::class.java))
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], ObjectValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Ship already exists on board."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldHandleShipCollisions() {
        doThrow(ShipCollisionCheck()).`when`<BoardService>(mockService).placePiece(anyLong(), anyListOf(Piece::class.java))
        val pieces = listOf(Piece.build {
            withId { 1 }
            withType { DESTROYER }
            withPlacement { Point(0, 0) }
            withOrientation { DOWN }
        })

        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pieces.toString())).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], ObjectValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("Ship collision."))
    }

    @Test
    @Throws(Exception::class)
    fun shouldNeedAnId() {
        doThrow(ShipCollisionCheck()).`when`<BoardService>(mockService).placePiece(anyLong(), anyListOf(Piece::class.java))
        val actual = mapper.readValue(mockMvc.perform(put("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\n" +
                        "    \"type\": \"DESTROYER\",\n" +
                        "    \"placement\": {\n" +
                        "      \"x\": 8,\n" +
                        "      \"y\": 0\n" +
                        "    },\n" +
                        "    \"orientation\": \"DOWN\"\n" +
                        "}]"
                )).andReturn()
                .response
                .contentAsString, GameErrors::class.java)

        val error = mapper.convertValue(actual.errors[0], FieldValidation::class.java)
        assertThat(error.validations[0].message, CoreMatchers.`is`("An Id is missing."))
    }

    private fun getFieldErrorMessages(actual: GameErrors): List<String> {
        return mapper.convertValue(actual.errors, Array<FieldValidation>::class.java)
                .map { it.validations }
                .flatten()
                .map { it.message }
    }
}