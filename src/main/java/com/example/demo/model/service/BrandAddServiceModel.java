package com.example.demo.model.service;

import javax.validation.constraints.NotNull;

public class BrandAddServiceModel {

    @NotNull
    private String name;
    private String model;

    public String getModel() {
        return model;
    }

    public BrandAddServiceModel setModel(String model) {
        this.model = model;
        return this;
    }

    public String getName() {
        return name;
    }

    public BrandAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }
}
