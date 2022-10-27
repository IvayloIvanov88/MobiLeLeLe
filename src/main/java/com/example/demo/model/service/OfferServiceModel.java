package com.example.demo.model.service;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import com.example.demo.model.validation.YearInPastOrPresent;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
public class OfferServiceModel {

    @NotNull
    private EngineEnum engine;

    @NotEmpty
    private String imageUrl;

    @NotNull
    @PositiveOrZero
    private Integer mileage;

    @NotNull
    @DecimalMin("100")
    private BigDecimal price;

    @NotNull
    private TransmissionEnum transmission;

    @YearInPastOrPresent(minYear = 1930)
    private Integer year;

    @NotEmpty
    @Size(min = 10, max = 9999)
    private String description;

    @NotNull
    private Long modelId;


}
