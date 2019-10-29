package com.gajaharan.offer.controller;

import com.gajaharan.offer.model.Offer;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

/**
 * Created by gajaharan on 29/10/2019.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class OfferControllerTest {
    private static final String OFFERS_ENDPOINT = "/offers/";
    private static final Long OFFER_ID = 1l;

    @LocalServerPort
    private int serverPort;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void createOffer_shouldReturn204AndLocationHeader() {
        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(createOffer()).
                log().everything().
            when().
                post(OFFERS_ENDPOINT).
            then().
                log().everything().
                statusCode(HttpStatus.SC_CREATED).
                header("Location", RestAssured.baseURI + ":" + RestAssured.port + "/offers/" + OFFER_ID);
    }

    private Offer createOffer() {

        Offer offer = new Offer();
        offer.setAmount(new BigDecimal(2.0));
        offer.setDescription("Test offer");
        offer.setCurrency("GBP");
        offer.setStartDate("01-01-2020");
        offer.setEndDate("02-01-2020");
        return offer;
    }

}
