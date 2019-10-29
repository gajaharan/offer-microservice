package com.gajaharan.offer.controller;

import com.gajaharan.offer.model.Offer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by gajaharan on 29/10/2019.
 */
@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
@Slf4j
public class OfferController {

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOffer (@RequestBody Offer offer) {
        log.info("Called POST /offers with payload: {}", offer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand("1").toUri();
        return ResponseEntity.created(location).build();
    }
}
