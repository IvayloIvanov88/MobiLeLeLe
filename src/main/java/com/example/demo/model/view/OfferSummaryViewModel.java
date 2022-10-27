package com.example.demo.model.view;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferSummaryViewModel {

    private Long id;

    private String description;

    private EngineEnum engine;

    private String imageUrl;

    private double mileage;

    private BigDecimal price;

    private TransmissionEnum transmission;

    private int year;

    private String model;

    private String brand;

    private Instant created;

    private Instant updated;
    private String seller;

}