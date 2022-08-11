package com.example.demo.model.view;

import com.example.demo.model.entity.enums.ModelCategoryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ModelViewModel {

    private String name;
    private ModelCategoryEnum category;
    private int startYear;
    private Integer endYear;
    private String imageUrl;

}
