package com.gajaharan.offer.repository;

import com.gajaharan.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gajaharan on 29/10/2019.
 */
@Repository
public interface OfferRepository  extends JpaRepository<Offer, Long> {
}
