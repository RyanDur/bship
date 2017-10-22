package com.bship.integration

import com.bship.DBHelper
import com.bship.games.endpoints.cabinet.entity.Board
import com.bship.games.endpoints.cabinet.entity.Game
import com.bship.games.endpoints.cabinet.entity.Point
import com.bship.games.logic.definitions.Direction.DOWN
import com.bship.games.logic.definitions.Direction.RIGHT
import com.bship.games.logic.definitions.Harbor.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardSetup {

    private var gameId: Long = 0
    private lateinit var board1: Board
    private lateinit var board2: Board

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Before
    fun setup() {
        DBHelper.reset()
        val result = mockMvc.perform(post("/games/BATTLESHIP")
                .contentType(MediaType.APPLICATION_JSON)).andReturn()
        val game = mapper.readValue(result.response.contentAsString, Game::class.java)
        gameId = game.id
        val boards = game.boards.sortedWith(compareBy(Board::id))
        board1 = boards.first()
        board2 = boards.last()
    }

    @Test
    fun `should be able to set a ship on the board`() {
        val carrier = board1.pieces.first { it.type == AIRCRAFT_CARRIER }
                .copy {
                    withPlacement { Point(0, 0) }
                    withOrientation { DOWN }
                }

        val content = mockMvc.perform(put("/boards/${board1.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(listOf(carrier).toString()))
                .andReturn()
                .response
                .contentAsString

        val actual = mapper.readValue(content, Board::class.java)

        assertThat(actual.pieces.first { it.type == AIRCRAFT_CARRIER })
                .isEqualToComparingFieldByField(carrier)
    }

    @Test
    fun `should be able to set up a board`() {
        val ships = listOf(
                board1.pieces.first { it.type == AIRCRAFT_CARRIER }
                        .copy {
                            withPlacement { Point(0, 0) }
                            withOrientation { DOWN }
                        },
                board1.pieces.first { it.type == BATTLESHIP }
                        .copy {
                            withPlacement { Point(1, 0) }
                            withOrientation { DOWN }
                        },
                board1.pieces.first { it.type == SUBMARINE }
                        .copy {
                            withPlacement { Point(2, 0) }
                            withOrientation { DOWN }
                        },
                board1.pieces.first { it.type == CRUISER }
                        .copy {
                            withPlacement { Point(3, 0) }
                            withOrientation { DOWN }
                        },
                board1.pieces.first { it.type == DESTROYER }
                        .copy {
                            withPlacement { Point(4, 0) }
                            withOrientation { DOWN }
                        })

        val content = mockMvc.perform(put("/boards/${board1.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ships.toString()))
                .andReturn()
                .response
                .contentAsString

        val actual = mapper.readValue(content, Board::class.java)

        assertThat(actual.pieces).containsAll(ships)
    }


    @Test
    fun `should not be able to set the same piece more than once`() {
        val carrier1 = board1.pieces.first { it.type == AIRCRAFT_CARRIER }
                .copy {
                    withPlacement { Point(0, 0) }
                    withOrientation { DOWN }
                }

        val carrier2 = board1.pieces.first { it.type == AIRCRAFT_CARRIER }
                .copy {
                    withPlacement { Point(1, 0) }
                    withOrientation { DOWN }
                }

        val content = mockMvc.perform(put("/boards/${board1.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(listOf(carrier1, carrier2).toString()))
                .andExpect { status().isBadRequest }
                .andReturn()
                .resolvedException

        assertThat(content.message).contains("Duplicate ship.")
    }

    @Test
    fun `should not be able to place a piece upon another piece`() {
        val carrier = board1.pieces.first { it.type == AIRCRAFT_CARRIER }
                .copy {
                    withPlacement { Point(0, 0) }
                    withOrientation { DOWN }
                }

        val battleship = board1.pieces.first { it.type == BATTLESHIP }
                .copy {
                    withPlacement { Point(0, 1) }
                    withOrientation { RIGHT }
                }

        val content = mockMvc.perform(put("/boards/${board1.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(listOf(carrier, battleship).toString()))
                .andExpect { status().isBadRequest }
                .andReturn()
                .resolvedException

        assertThat(content.message).contains("Ship collision.")
    }
}