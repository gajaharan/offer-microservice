package com.gajaharan.offer.util;

import com.gajaharan.offer.model.Offer;

import java.math.BigDecimal;

/**
 * Created by gajaharan on 29/10/2019.
 */
public class RequestBuilder {
    public static Offer createOffer() {

        Offer offer = new Offer();
        offer.setAmount(new BigDecimal(2.0));
        offer.setCurrency("GBP");
        offer.setDescription("Test offer");
        offer.setStartDate("01-01-2020");
        offer.setEndDate("02-01-2020");
        return offer;
    }
}
