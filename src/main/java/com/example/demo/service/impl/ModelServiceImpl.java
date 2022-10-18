package com.example.demo.service.impl;

import com.example.demo.model.entity.ModelEntity;
import com.example.demo.model.service.ModelAddServiceModel;
import com.example.demo.repository.ModelRepository;
import com.example.demo.service.BrandService;
import com.example.demo.service.ModelService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandService brandService;
    private final ModelMapper modelMapper;

    @Override
    public long save(ModelAddServiceModel model) {
        ModelEntity newModel = modelMapper.map(model, ModelEntity.class);

        return modelRepository.save(newModel).getId();
    }
}
