package com.gajaharan.offer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gajaharan on 30/10/2019.
 */
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OfferNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public OfferNotFoundException(String message) {
        super();
        this.message = message;
    }
}
