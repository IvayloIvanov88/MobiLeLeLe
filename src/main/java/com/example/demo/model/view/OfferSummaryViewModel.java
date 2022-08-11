package com.example.demo.model.view;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OfferSummaryViewModel {


    private EngineEnum engine;
    private String imageUrl;
    private int mileage;
    private BigDecimal price;
    private TransmissionEnum transmission;
}
