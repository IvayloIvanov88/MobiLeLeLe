package com.example.demo.model.entity;

import com.example.demo.model.entity.enums.ModelCategoryEnum;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ModelCategoryEnum category;

    @Column(name = "image", length = 2024)
    private String imageUrl;

    @Column(name = "start_year")
    private int startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;
}
