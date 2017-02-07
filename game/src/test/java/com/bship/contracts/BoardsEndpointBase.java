package com.bship.contracts;

import com.bship.BattleshipApplication;
import com.bship.DBHelper;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BattleshipApplication.class,
        webEnvironment = RANDOM_PORT)
abstract public class BoardsEndpointBase {

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        DBHelper.reset();
        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssuredMockMvc.post(URI.create("/games"));
    }
}
