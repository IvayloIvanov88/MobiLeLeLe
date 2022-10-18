package com.example.demo.service.impl;

import com.example.demo.model.entity.BrandEntity;
import com.example.demo.model.entity.ModelEntity;
import com.example.demo.model.service.BrandAddServiceModel;
import com.example.demo.model.service.ModelAddServiceModel;
import com.example.demo.model.view.BrandViewModel;
import com.example.demo.model.view.ModelViewModel;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.ModelRepository;
import com.example.demo.service.BrandService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Override
    public void delete(long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandViewModel> getAllBrands() {

        List<BrandViewModel> result = new ArrayList<>();
        List<ModelEntity> allModels = modelRepository.findAll();

        allModels.forEach(me -> {
            BrandEntity brandEntity = me.getBrand();
            Optional<BrandViewModel> brandViewModelOpt = findByName(result, brandEntity.getName());
            if (brandViewModelOpt.isEmpty()) {
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



    @Override
    public BrandViewModel getById(Long id) {
        return modelMapper.map(brandRepository.findById(id), BrandViewModel.class);
    }

    @Override
    public long save(BrandAddServiceModel brandModel) {
        BrandEntity newBrand = modelMapper.map(brandModel, BrandEntity.class);
        ModelEntity newModel = new ModelEntity().setName(brandModel.getModel()).setBrand(newBrand);
        modelRepository.save(newModel);

        return brandRepository.save(newBrand).getId();
    }

    @Override
    public long saveModel(ModelAddServiceModel model) {
        BrandEntity brand = brandRepository.findByName(model.getBrandName());
        ModelEntity newModel = new ModelEntity()
                .setName(model.getName())
                .setBrand(brand)
                .setCategory(model.getCategory())
                .setImageUrl(model.getImageUrl())
                .setStartYear(model.getStartYear())
                .setEndYear(model.getEndYear());
        return modelRepository.save(newModel).getId();
    }

    private static Optional<BrandViewModel> findByName(List<BrandViewModel> allModels, String name) {
        return allModels.stream().filter(model -> model.getName().equals(name)).findAny();
    }
}
