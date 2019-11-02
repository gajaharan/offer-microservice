package com.gajaharan.offer.controller;

import com.gajaharan.offer.exception.OfferNotFoundException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.model.OfferRequest;
import com.gajaharan.offer.model.OfferResponse;
import com.gajaharan.offer.model.OfferStatus;
import com.gajaharan.offer.service.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by gajaharan on 29/10/2019.
 */
@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
@Slf4j
public class OfferController {

    private final OfferService offerService;

    private final ModelMapper modelMapper;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOffer (@Valid @RequestBody OfferRequest offerRequest) {
        log.info("Called POST /offers with payload: {}", offerRequest);

        Offer offer = modelMapper.map(offerRequest, Offer.class);
        offer.setStatus(OfferStatus.ACTIVE);

        Long id = offerService.createOffer(offer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfferResponse> getOfferById (@PathVariable("id") String id) throws OfferNotFoundException {
        log.info("Called GET /offers/{}", id);

        Offer retrievedOffer = offerService.getOfferById(id);
        OfferResponse offerResponse = modelMapper.map(retrievedOffer, OfferResponse.class);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}/cancel")
    public ResponseEntity<Void> cancelOfferById (@PathVariable("id") String id) throws OfferNotFoundException {
        log.info("Called GET /offers/{}/cancel", id);

        offerService.cancelOfferById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
