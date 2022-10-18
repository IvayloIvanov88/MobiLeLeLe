package com.example.demo.model.service;

import com.example.demo.model.entity.enums.ModelCategoryEnum;

import javax.validation.constraints.NotNull;

public class ModelAddServiceModel {
    @NotNull
    private String name;
    @NotNull
    private ModelCategoryEnum category;
    @NotNull
    private String imageUrl;
    @NotNull
    private int startYear;
    @NotNull
    private Integer endYear;
    @NotNull
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public ModelAddServiceModel setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public String getName() {
        return name;
    }

    public ModelAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public ModelCategoryEnum getCategory() {
        return category;
    }

    public ModelAddServiceModel setCategory(ModelCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ModelAddServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public int getStartYear() {
        return startYear;
    }

    public ModelAddServiceModel setStartYear(int startYear) {
        this.startYear = startYear;
        return this;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public ModelAddServiceModel setEndYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }


}