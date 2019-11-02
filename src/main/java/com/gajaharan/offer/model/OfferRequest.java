package com.gajaharan.offer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by gajaharan on 31/10/2019.
 */
@Data
public class OfferRequest {

    @NotEmpty
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotEmpty
    private String currency;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
}
