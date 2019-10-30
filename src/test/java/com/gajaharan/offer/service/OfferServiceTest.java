package com.gajaharan.offer.service;

import com.gajaharan.offer.exception.OfferConflictException;
import com.gajaharan.offer.exception.OfferNotFoundException;
import com.gajaharan.offer.exception.OfferServiceException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.model.OfferStatus;
import com.gajaharan.offer.repository.OfferRepository;
import com.gajaharan.offer.util.RequestBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gajaharan on 29/10/2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {
    private static final Long OFFER_ID = 1l;

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private Offer mockOffer;

    @Test
    public void createOffer_shouldSaveOffer() throws OfferServiceException {


        Offer createdOffer = RequestBuilder.createOffer();

        when(mockOfferRepository.save(createdOffer))
                .thenReturn(mockOffer);
        when(mockOffer.getId()).thenReturn(OFFER_ID);

        Long actualOfferId = offerService.createOffer(createdOffer);

        assertThat(actualOfferId, is(OFFER_ID));
    }

    @Test(expected = OfferServiceException.class)
    public void createOffer_shouldThrowOfferServiceException() throws OfferServiceException {
        Offer createdOffer = RequestBuilder.createOffer();
        when(mockOfferRepository.save(createdOffer))
                .thenThrow(new OfferServiceException("Error"));

        offerService.createOffer(createdOffer);
    }

    @Test
    public void getOfferByID_shouldReturnOffer() {
        Offer retrievedOffer = RequestBuilder.createOffer();

        when(mockOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(retrievedOffer));

        Offer actualOffer = offerService.getOfferById(String.valueOf(OFFER_ID));

        assertThat(actualOffer, is(retrievedOffer));
    }

    @Test(expected = OfferNotFoundException.class)
    public void getOfferByID_shouldThrowOfferNotFoundException() {
        when(mockOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.empty());

        offerService.getOfferById(String.valueOf(OFFER_ID));
    }

    @Test
    public void cancelOffer_shouldBeSuccessful() throws OfferNotFoundException {

        when(mockOffer.getEndDate()).thenReturn(LocalDateTime.now());
        when(mockOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(mockOffer));

        offerService.cancelOfferById(String.valueOf(OFFER_ID));

        verify(mockOffer).setStatus(OfferStatus.CANCELLED);
    }

    @Test(expected = OfferConflictException.class)
    public void cancelOffer_shouldThrowConflict() throws OfferNotFoundException {

        when(mockOffer.getEndDate()).thenReturn(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        when(mockOffer.getStatus()).thenReturn(OfferStatus.EXPIRED);
        when(mockOfferRepository.findById(OFFER_ID))
                .thenReturn(Optional.of(mockOffer));

        offerService.cancelOfferById(String.valueOf(OFFER_ID));
    }

    @Test(expected = OfferConflictException.class)
    public void cancelOffer_shouldThrowCancelConflict() throws OfferNotFoundException {
        Long returnedOfferID = 1l;

        when(mockOffer.getEndDate()).thenReturn(LocalDateTime.now());
        when(mockOffer.getStatus()).thenReturn(OfferStatus.CANCELLED);
        when(mockOfferRepository.findById(returnedOfferID))
                .thenReturn(Optional.of(mockOffer));

        offerService.cancelOfferById(String.valueOf(OFFER_ID));
    }
}
