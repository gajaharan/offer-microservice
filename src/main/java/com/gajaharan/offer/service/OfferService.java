package com.gajaharan.offer.service;

import com.gajaharan.offer.exception.OfferNotFoundException;
import com.gajaharan.offer.exception.OfferServiceException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Created by gajaharan on 29/10/2019.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public Long createOffer(Offer offer) throws OfferServiceException {
        try {
            Offer createdOffer = offerRepository.save(offer);
            return createdOffer.getId();
        } catch (DataAccessException ex) {
            log.error("Unable to save {}", offer, ex);
            throw new OfferServiceException("Unable to save " + offer);
        }
    }

    public Offer getOfferById(String id) {
        return  offerRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new OfferNotFoundException(String.format("Offer Not found for id: %s", id)));
    }
}
