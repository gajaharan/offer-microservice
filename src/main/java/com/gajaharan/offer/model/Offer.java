package com.gajaharan.offer.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by gajaharan on 29/10/2019.
 */
@Data
public class Offer {
    @NotNull
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
