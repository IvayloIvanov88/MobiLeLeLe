package com.example.demo.model.view;

import com.example.demo.model.entity.enums.ModelCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModelViewModel {

    private Long id;
    private String name;
    private ModelCategoryEnum category;
    private int startYear;
    private Integer endYear;
    private String imageUrl;

}
