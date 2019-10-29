package com.gajaharan.offer.util;

import com.gajaharan.offer.model.Offer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by gajaharan on 29/10/2019.
 */
public class RequestBuilder {
    public static Offer createOffer() {

        Offer offer = new Offer();
        offer.setAmount(new BigDecimal(2.0));
        offer.setCurrency("GBP");
        offer.setDescription("Test offer");
        offer.setStartDate(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        offer.setEndDate(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        return offer;
    }
}
