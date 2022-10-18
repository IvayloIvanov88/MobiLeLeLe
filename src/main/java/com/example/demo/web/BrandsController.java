package com.example.demo.web;

import com.example.demo.model.binding.OfferUpdateBindingModel;
import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import com.example.demo.model.service.BrandAddServiceModel;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.service.OfferUpdateServiceModel;
import com.example.demo.model.view.BrandViewModel;
import com.example.demo.model.view.OfferSummaryViewModel;
import com.example.demo.service.BrandService;
import com.example.demo.service.OfferService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final OfferService offerService;


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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/brands/{id}/update")
    public String updateOffer(@PathVariable Long id, Model model) {
//        todo
        OfferUpdateBindingModel updateBindingModel = modelMapper
                .map(offerService.getOfferById(id), OfferUpdateBindingModel.class);
        model.addAttribute("offerModel", updateBindingModel)
                .addAttribute("engines", EngineEnum.values())
                .addAttribute("transmissions", TransmissionEnum.values());
        return "brand-update";
    }

    @GetMapping("/{id}/update/errors")
    public String addBrandErrors(@PathVariable Long id, Model model) {
        model.
                addAttribute("engines", EngineEnum.values()).
                addAttribute("transmissions", TransmissionEnum.values());
        return "brand-update";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/update")
    public String updateBrand(@PathVariable Long id,
                              @Valid OfferUpdateBindingModel offerModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.
                    addFlashAttribute("brands", offerModel).
                    addFlashAttribute("org.springframework.validation.BindingResult.offerModel", bindingResult);

            return "redirect:/brands/" + id + "/update/errors";
        }
//        offerService.updateOffer(modelMapper.map(offerModel, OfferUpdateServiceModel.class));

        return "redirect:/brands/" + id + "/brand-details";
    }
}
