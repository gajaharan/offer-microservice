package com.gajaharan.offer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gajaharan on 29/10/2019.
 */
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OfferServiceException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public OfferServiceException(String message) {
        super();
        this.message = message;
    }
}
