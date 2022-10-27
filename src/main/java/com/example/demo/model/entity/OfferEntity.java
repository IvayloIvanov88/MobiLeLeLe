package com.example.demo.model.entity;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EngineEnum engine;
    @Column(name = "image_url")
    private String imageUrl;

    private double mileage;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TransmissionEnum transmission;

    @ManyToOne
    private ModelEntity model;

    private int year;

    @ManyToOne
    private UserEntity seller;

    private String description;

}
