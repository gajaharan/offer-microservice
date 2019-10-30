package com.gajaharan.offer.service;

import com.gajaharan.offer.exception.OfferConflictException;
import com.gajaharan.offer.exception.OfferNotFoundException;
import com.gajaharan.offer.exception.OfferServiceException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.model.OfferStatus;
import com.gajaharan.offer.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by gajaharan on 29/10/2019.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public Long createOffer(Offer offer) throws OfferServiceException {
        offer.setStatus(OfferStatus.ACTIVE);
        try {
            Offer createdOffer = offerRepository.save(offer);
            return createdOffer.getId();
        } catch (DataAccessException ex) {
            log.error("Unable to save {}", offer, ex);
            throw new OfferServiceException("Unable to save " + offer);
        }
    }

    public Offer getOfferById(String id) {
        Optional<Offer> retrievedOffer = offerRepository.findById(Long.valueOf(id));

        if (retrievedOffer.isPresent()) {
            Offer offer = retrievedOffer.get();
            if (LocalDateTime.now().isAfter(offer.getEndDate())) {
                offer.setStatus(OfferStatus.EXPIRED);
                offerRepository.save(offer);
            }
            return offer;
        } else {
            throw new OfferNotFoundException(String.format("Offer Not found for id: %s", id));
        }
    }

    public boolean cancelOfferById(String id) throws OfferNotFoundException {
        Offer offer = getOfferById(id);
        if (OfferStatus.EXPIRED.equals(offer.getStatus())) {
            throw new OfferConflictException(String.format("Offer %s already expired", id));
        }

        if (OfferStatus.CANCELLED.equals(offer.getStatus())) {
            throw new OfferConflictException(String.format("Offer %s already cancelled", id));
        }

        offer.setStatus(OfferStatus.CANCELLED);
        offerRepository.save(offer);
        return true;
    }
}
