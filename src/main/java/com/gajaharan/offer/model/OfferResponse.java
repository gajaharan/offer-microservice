package com.gajaharan.offer.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by gajaharan on 31/10/2019.
 */
@Data
public class OfferResponse extends OfferRequest {

    @NotEmpty
    private Long id;

    @NotNull
    private OfferStatus status;
}
