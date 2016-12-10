package com.bship.games.endpoints;

import com.bship.games.domains.Harbor;
import com.bship.games.domains.Point;
import com.bship.games.domains.Ship;
import com.bship.games.exceptions.ShipCollisionCheck;
import com.bship.games.exceptions.ShipExistsCheck;
import com.bship.games.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class BoardsControllerTest {

    private BoardsController boardsController;
    private GameService mockService;

    @Before
    public void setup() {
        mockService = mock(GameService.class);
        boardsController = new BoardsController(mockService);
    }

    @Test
    public void placeShip_methodSignatureBindToPathParamsAndRequestBody() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(boardsController).build();
        mockMvc.perform(post("/boards/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"type\": \"BATTLESHIP\",\n" +
                        "  \"start\": {\n" +
                        "    \"x\": 4,\n" +
                        "    \"y\": 5\n" +
                        "  },\n" +
                        "  \"end\": {\n" +
                        "    \"x\": 1,\n" +
                        "    \"y\": 5\n" +
                        "  }\n" +
                        "}"
                ));

        ArgumentCaptor<Ship> captor = ArgumentCaptor.forClass(Ship.class);

        verify(mockService).placeShip(eq(9L), captor.capture());
        Ship capturedShip = captor.getValue();
        assertEquals(Harbor.BATTLESHIP, capturedShip.getType());

        assertThat(4, is(equalTo(capturedShip.getStart().getX())));
        assertThat(5, is(equalTo(capturedShip.getStart().getY())));
        assertThat(1, is(equalTo(capturedShip.getEnd().getX())));
        assertThat(5, is(equalTo(capturedShip.getEnd().getY())));
    }

    @Test
    public void placeShip_passesTheShipObjectToTheServiceLayerForTheGivenGameAndBoard() throws ShipExistsCheck, ShipCollisionCheck {
        Ship ship = Ship.builder()
                .withType(Harbor.SUBMARINE)
                .withStart(new Point())
                .withEnd(new Point()).build();

        Long boardId = 90L;
        boardsController.placeShip(boardId, ship);

        verify(mockService).placeShip(boardId, ship);
    }

}