package com.example.demo.service;

import com.example.demo.model.view.BrandViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BrandService {

    List<BrandViewModel> getAllBrands();
}
