package com.example.demo.model.service;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferUpdateServiceModel {

    private Long id;

    private String description;

    private EngineEnum engine;

    private String imageUrl;

    private double mileage;

    private BigDecimal price;

    private TransmissionEnum transmission;

    private int year;

}
