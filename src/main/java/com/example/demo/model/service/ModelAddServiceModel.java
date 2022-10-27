package com.example.demo.model.service;

import com.example.demo.model.entity.enums.ModelCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}