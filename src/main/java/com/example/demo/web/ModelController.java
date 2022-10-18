package com.example.demo.web;

import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.ModelCategoryEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import com.example.demo.model.service.BrandAddServiceModel;
import com.example.demo.model.service.ModelAddServiceModel;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.service.BrandService;
import com.example.demo.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/models")
@AllArgsConstructor
public class ModelController {

    private final BrandService brandService;

    @ModelAttribute("model")
    public ModelAddServiceModel brandModel() {
        return new ModelAddServiceModel();
    }


    @GetMapping("/add")
    public String addModel(Model model){
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("category", ModelCategoryEnum.values());
        return "model-add";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addBrand(@Valid @ModelAttribute ModelAddServiceModel model, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("model", model);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.model", bindingResult);
            return "redirect:/models/add";
        }
        long newBrand = brandService.saveModel(model);
        return "redirect:/brands/" + newBrand + "/model-details";
        //todo model-details
    }
}
