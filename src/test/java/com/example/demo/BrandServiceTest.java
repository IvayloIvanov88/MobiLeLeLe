package com.example.demo;

import com.example.demo.model.entity.BrandEntity;
import com.example.demo.model.entity.ModelEntity;
import com.example.demo.model.view.BrandViewModel;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.ModelRepository;
import com.example.demo.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    private BrandServiceImpl serviceToTest;

    @Mock
    ModelRepository mockModelRepository;
    @Mock
    BrandRepository mockBrandRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new BrandServiceImpl(mockModelRepository, mockBrandRepository, new ModelMapper());

    }

    @Test
    public void testGetAllBrands() {
        // Arrange
        BrandEntity brandAudi = new BrandEntity();
        brandAudi.setId(1234L);
        brandAudi.setName("Audi");

        BrandEntity brandVW = new BrandEntity();
        brandAudi.setId(333L);
        brandAudi.setName("VW");


        ModelEntity modelAudi = new ModelEntity();
        modelAudi.setBrand(brandAudi);
        modelAudi.setId(22222L);
        modelAudi.setName("E-tron");

        ModelEntity modelVW = new ModelEntity();
        modelVW.setBrand(brandVW);
        modelVW.setId(22222L);
        modelVW.setName("Golf");

        when(mockModelRepository.findAll()).thenReturn(List.of(modelAudi, modelVW));
        //act
        List<BrandViewModel> allModels = serviceToTest.getAllBrands();

        //assert
        BrandViewModel actualAudi = allModels.get(0);
        BrandViewModel actualVW = allModels.get(1);

        Assertions.assertEquals(2, allModels.size());

        Assertions.assertEquals(modelAudi.getName(), actualAudi.getModels().get(0).getName());
        Assertions.assertEquals(modelAudi.getId(), actualAudi.getModels().get(0).getId());

        Assertions.assertEquals(modelVW.getName(), actualVW.getModels().get(0).getName());
        Assertions.assertEquals(modelVW.getId(), actualVW.getModels().get(0).getId());
    }

}
