package com.gajaharan.offer.controller;

import com.gajaharan.offer.exception.OfferNotFoundException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.model.OfferRequest;
import com.gajaharan.offer.service.OfferService;
import com.gajaharan.offer.util.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Created by gajaharan on 29/10/2019.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class OfferControllerTest {
    private static final String OFFERS_ENDPOINT = "/offers/";
    private static final String OFFER_ID = "1";

    @LocalServerPort
    private int serverPort;

    @MockBean
    private OfferService mockOfferService;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void createOffer_shouldReturn201AndLocationHeader() {
        Long offerId = 0l;
        Offer createdOffer = RequestBuilder.createOffer();
        OfferRequest offerRequest = RequestBuilder.createOfferRequest();
        when(mockOfferService.createOffer(createdOffer)).thenReturn(offerId);

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(offerRequest).
                log().everything().
        when().
                post(OFFERS_ENDPOINT).
        then().
                log().everything().
                statusCode(HttpStatus.SC_CREATED).
                header("Location", RestAssured.baseURI + ":" + RestAssured.port + "/offers/" + offerId);
    }

    @Test
    public void createOffer_shouldReturn400AndFriendErrorMessage() {
        OfferRequest offerRequest = RequestBuilder.createInvalidOfferRequest();
        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(offerRequest).
                log().everything().
        when().
                post(OFFERS_ENDPOINT).
        then().
                log().everything().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("amount", is(equalTo("The amount must be greater than 0"))).
                body("currency", is(equalTo("must not be empty")));
    }

    @Test
    public void getOfferByID_shouldReturn200AndBody() {
        Offer retrievedOffer = RequestBuilder.createOffer();

        when(mockOfferService.getOfferById(OFFER_ID)).thenReturn(retrievedOffer);

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                log().everything().
        when().
                get(OFFERS_ENDPOINT + "{id}", OFFER_ID).
        then().
                log().everything().
                statusCode(HttpStatus.SC_OK).
                body("currency", is(equalTo("GBP"))).
                body("description", is(equalTo("Test offer")));
    }

    @Test
    public void getOfferByID_shouldReturn404(){
        when(mockOfferService.getOfferById(OFFER_ID)).thenThrow(new OfferNotFoundException("Error"));

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                log().everything().
                when().
                get(OFFERS_ENDPOINT+"{id}", OFFER_ID).
                then().
                log().everything().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void cancelOffer_shouldReturn200() throws OfferNotFoundException {;
        when(mockOfferService.cancelOfferById(String.valueOf(OFFER_ID))).thenReturn(true);

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                log().everything().
                when().
                delete(OFFERS_ENDPOINT+"{id}/cancel", OFFER_ID).
                then().
                log().everything().
                statusCode(HttpStatus.SC_OK);
    }

}
