package com.example.demo.web;

import com.example.demo.model.binding.OfferUpdateBindingModel;
import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.service.OfferUpdateServiceModel;
import com.example.demo.model.view.OfferSummaryViewModel;
import com.example.demo.service.BrandService;
import com.example.demo.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }


    @ModelAttribute("offerModel")
    public OfferServiceModel offerModel() {
        return new OfferServiceModel();
    }

    @GetMapping("/all")
    public String getAllOffers(Model model) {
        model.addAttribute("offers", offerService.getAllOffers());
        return "offers";
    }

    @GetMapping("/add")
    public String newOffer(Model model) {
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("engines", EngineEnum.values());
        model.addAttribute("transmissions", TransmissionEnum.values());

        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid @ModelAttribute OfferServiceModel offerModel, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerModel", offerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.offerModel",
                    bindingResult);
            return "redirect:/offers/add";
        }

        long newOfferId = offerService.save(offerModel);
        return "redirect:/offers/" + newOfferId + "/details";
    }

    @GetMapping("/offer/{id}")
    public String offerDetails(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "details";
    }

    @GetMapping("/{id}/details")
    private String getOfferDetails(@PathVariable Long id, Model model) {
        OfferSummaryViewModel offerView = offerService.getOfferById(id);
        model.addAttribute("offer", offerView);
        return "details";
    }

    @GetMapping("/offer/{id}/update")
    public String updateOffer(@PathVariable Long id, Model model) {
        OfferUpdateBindingModel updateBindingModel = modelMapper
                .map(offerService.getOfferById(id), OfferUpdateBindingModel.class);

        model.addAttribute("offerModel", updateBindingModel)
                .addAttribute("engines", EngineEnum.values())
                .addAttribute("transmissions", TransmissionEnum.values());
        return "update";
    }

    @GetMapping("/{id}/update/errors")
    public String updateOfferErrors(@PathVariable Long id, Model model) {
        model.addAttribute("engines", EngineEnum.values())
                .addAttribute("transmissions", TransmissionEnum.values());
        return "update";
    }

    @PatchMapping("/offer/{id}/update")
    public String updateOffer(@PathVariable Long id,
                              @Valid OfferUpdateBindingModel offerModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("offerModel", offerModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerModel", bindingResult);

            return "redirect:/offers/" + id + "/update/errors";
        }
        offerService.updateOffer(modelMapper.map(offerModel, OfferUpdateServiceModel.class));

        return "redirect:/offers/" + id + "/details";
    }
    @DeleteMapping("/offer/{id}")
    public String delete(@PathVariable long id, Model model) {

        offerService.delete(id);

        return "redirect:/offers/all";
    }
}
