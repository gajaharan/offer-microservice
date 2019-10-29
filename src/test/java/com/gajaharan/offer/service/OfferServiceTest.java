package com.gajaharan.offer.service;

import com.gajaharan.offer.exception.OfferServiceException;
import com.gajaharan.offer.model.Offer;
import com.gajaharan.offer.repository.OfferRepository;
import com.gajaharan.offer.util.RequestBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Created by gajaharan on 29/10/2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {
    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private Offer mockOffer;

    @Test
    public void createOffer_shouldReturnCREATEDAndLocationHeader() throws OfferServiceException {

        Long returnedOfferID = 1l;
        Offer createdOffer = RequestBuilder.createOffer();

        when(mockOfferRepository.save(createdOffer))
                .thenReturn(mockOffer);
        when(mockOffer.getId()).thenReturn(returnedOfferID);

        Long offerId = offerService.createOffer(createdOffer);

        assertThat(offerId, is(returnedOfferID));
    }

    @Test (expected = OfferServiceException.class)
    public void createOffer_shouldThrowOfferServiceException() throws OfferServiceException {
        Offer createdOffer = RequestBuilder.createOffer();
        when(mockOfferRepository.save(createdOffer))
                .thenThrow(new OfferServiceException("Error"));

        offerService.createOffer(createdOffer);
    }
}
