package com.gajaharan.offer.controller;

import com.gajaharan.offer.exception.OfferNotFoundException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.service.OfferService;
import com.gajaharan.offer.util.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

/**
 * Created by gajaharan on 29/10/2019.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class OfferControllerTest {
    private static final String OFFERS_ENDPOINT = "/offers/";
    private static final String OFFER_ID_PARAM = "id";
    private static final Long OFFER_ID = 1l;

    @LocalServerPort
    private int serverPort;

    @Mock
    OfferService mockOfferService;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void createOffer_shouldReturn201AndLocationHeader() {
        when(mockOfferService.createOffer(RequestBuilder.createOffer())).thenReturn(OFFER_ID);

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(RequestBuilder.createOffer()).
                log().everything().
        when().
                post(OFFERS_ENDPOINT).
        then().
                log().everything().
                statusCode(HttpStatus.SC_CREATED).
                header("Location", RestAssured.baseURI + ":" + RestAssured.port + "/offers/" + OFFER_ID);
    }

    @Ignore
    public void getOfferByID_shouldReturn200AndBody(){
        Offer retrievedOffer = RequestBuilder.createOffer();
        String offerId = "1";
        when(mockOfferService.getOfferById(offerId)).thenReturn(retrievedOffer);

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                log().everything().
        when().
                get(OFFERS_ENDPOINT+"{id}", offerId).
        then().
                log().everything().
                statusCode(HttpStatus.SC_OK);
    }

    @Ignore
    public void getOfferByID_shouldReturn404(){
        String offerId = "1";
        when(mockOfferService.getOfferById(offerId)).thenThrow(new OfferNotFoundException("Error"));

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                log().everything().
                when().
                get(OFFERS_ENDPOINT+"{id}", offerId).
                then().
                log().everything().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
