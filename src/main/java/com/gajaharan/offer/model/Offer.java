package com.gajaharan.offer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by gajaharan on 29/10/2019.
 */
@Data
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotEmpty
    private String currency;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String endDate;

}
