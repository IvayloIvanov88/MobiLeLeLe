package com.example.demo.service;

import com.example.demo.model.service.BrandAddServiceModel;
import com.example.demo.model.service.ModelAddServiceModel;
import com.example.demo.model.view.BrandViewModel;

import java.util.List;

public interface BrandService {

    void delete(long id);

    List<BrandViewModel> getAllBrands();

    BrandViewModel getById(Long id);

    long save(BrandAddServiceModel brandModel);
    long saveModel(ModelAddServiceModel model);
}
