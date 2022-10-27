package com.example.demo.model.view;

import com.example.demo.model.entity.enums.ModelCategoryEnum;
import lombok.*;

@Data
@Builder
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
