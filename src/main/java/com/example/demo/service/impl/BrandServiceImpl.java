package com.example.demo.service.impl;

import com.example.demo.model.entity.BrandEntity;
import com.example.demo.model.entity.ModelEntity;
import com.example.demo.model.view.BrandViewModel;
import com.example.demo.model.view.ModelViewModel;
import com.example.demo.repository.ModelRepository;
import com.example.demo.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandServiceImpl(ModelRepository modelRepository, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BrandViewModel> getAllBrands() {

        List<BrandViewModel> result = new ArrayList<>();
        List<ModelEntity> allModels = modelRepository.findAll();

        allModels.forEach(me -> {
            BrandEntity brandEntity = me.getBrand();
            Optional<BrandViewModel> brandViewModelOpt = findByName(result, brandEntity.getName());
            if (!brandViewModelOpt.isPresent()) {
                BrandViewModel newBrandViewModel = new BrandViewModel();
                modelMapper.map(brandEntity, newBrandViewModel);
                result.add(newBrandViewModel);
                brandViewModelOpt = Optional.of(newBrandViewModel);
            }
            ModelViewModel newModelViewModel = new ModelViewModel();
            modelMapper.map(me, newModelViewModel);
            brandViewModelOpt.get().addModel(newModelViewModel);
        });
        return result;
    }

    private static Optional<BrandViewModel> findByName(List<BrandViewModel> allModels, String name) {
        return allModels.stream().filter(model -> model.getName().equals(name)).findAny();
    }
}
