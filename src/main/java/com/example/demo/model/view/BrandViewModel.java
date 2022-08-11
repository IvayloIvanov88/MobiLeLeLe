package com.example.demo.model.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class BrandViewModel {

    private String name;
    private List<ModelViewModel> models = new ArrayList<>();

    public BrandViewModel addModel(ModelViewModel modelViewModel){
        this.models.add(modelViewModel);
        return this;
    }
}
