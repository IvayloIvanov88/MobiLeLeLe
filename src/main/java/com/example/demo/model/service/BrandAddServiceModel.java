package com.example.demo.model.service;

import com.example.demo.model.entity.ModelEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BrandAddServiceModel {

    @NotNull
    private String name;

    private List<ModelEntity> models = new ArrayList<>();

    public List<ModelEntity> getModels() {
        return models;
    }

    public BrandAddServiceModel setModels(List<ModelEntity> models) {
        this.models = models;
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
