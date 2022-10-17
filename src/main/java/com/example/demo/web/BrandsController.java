package com.example.demo.web;

import com.example.demo.model.service.BrandAddServiceModel;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.view.BrandViewModel;
import com.example.demo.model.view.OfferSummaryViewModel;
import com.example.demo.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/brands")
@AllArgsConstructor
public class BrandsController {

    private final BrandService brandService;


    @ModelAttribute("brands")
    public BrandAddServiceModel brandModel() {
        return new BrandAddServiceModel();
    }
    @GetMapping("/all")
    public String allBrands(Model model){
        model.addAttribute("brands", brandService.getAllBrands());
        return "brands";
    }

    @GetMapping("/add")
    public String addBrand(Model model){
        return "brand-add";
    }
    @PostMapping("/add")
    public String addBrand(@Valid @ModelAttribute BrandAddServiceModel brandModel, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("brands", brandModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.brandModel", bindingResult);
            return "redirect:/brands/add";
        }
        long newBrand = brandService.save(brandModel);
        return "redirect:/brands/" + newBrand + "/brand-details";
    }

    @GetMapping("/{id}/brand-details")
    public String getBrandDetails(@PathVariable Long id, Model model) {
        BrandViewModel brandView = brandService.getById(id);
        if (brandView == null) {
            return "redirect:/brands/add";
        }
        model.addAttribute("brands", brandView);
        return "brand-details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id, Model model) {

        brandService.delete(id);

        return "redirect:/brands/all";
    }
}
