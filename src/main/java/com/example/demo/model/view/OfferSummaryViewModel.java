package com.example.demo.model.view;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;

import java.math.BigDecimal;
import java.time.Instant;


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

    public Instant getCreated() {
        return created;
    }

    public OfferSummaryViewModel setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public Instant getUpdated() {
        return updated;
    }

    public OfferSummaryViewModel setUpdated(Instant updated) {
        this.updated = updated;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferSummaryViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public OfferSummaryViewModel setEngine(EngineEnum engine) {
        this.engine = engine;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferSummaryViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public double getMileage() {
        return mileage;
    }

    public OfferSummaryViewModel setMileage(double mileage) {
        this.mileage = mileage;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferSummaryViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public OfferSummaryViewModel setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public int getYear() {
        return year;
    }

    public OfferSummaryViewModel setYear(int year) {
        this.year = year;
        return this;
    }

    public String getModel() {
        return model;
    }

    public OfferSummaryViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public Long getId() {
        return id;
    }

    public OfferSummaryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public OfferSummaryViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getSeller() {
        return seller;
    }

    public OfferSummaryViewModel setSeller(String seller) {
        this.seller = seller;
        return this;
    }
}