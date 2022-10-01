package com.example.demo.service.impl;

import com.example.demo.model.entity.OfferEntity;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.service.OfferUpdateServiceModel;
import com.example.demo.model.view.OfferSummaryViewModel;
import com.example.demo.repository.ModelRepository;
import com.example.demo.repository.OfferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OfferService;
import com.example.demo.web.NotFoundObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, ModelRepository modelRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OfferSummaryViewModel getOfferById(Long id) {
//        OfferEntity offer = offerRepository.findById(id).get();
        Optional<OfferEntity> offer = offerRepository.findById(id);
        return offer.map(this::map).orElse(null);
    }

    @Override
    public List<OfferSummaryViewModel> getAllOffers() {
        return offerRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public long save(OfferServiceModel model) {
        OfferEntity offerEntity = asNewEntity(model);
        OfferEntity newEntity = offerRepository.save(offerEntity);
        return newEntity.getId();
    }

    @Override
    public void delete(long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public void updateOffer(OfferUpdateServiceModel serviceModel) {
        Long modelId = serviceModel.getId();

        OfferEntity offer = offerRepository.
                findById(modelId).
                orElseThrow(() ->
                        new NotFoundObjectException("Offer with id " + modelId + " is not found."));

        offer = setOffer(offer, serviceModel);

        offerRepository.save(offer);
    }


    private OfferEntity asNewEntity(OfferServiceModel model) {
        OfferEntity offerEntity = new OfferEntity();
        modelMapper.map(model, offerEntity);
        offerEntity.setId(null);

        offerEntity.setModel(modelRepository.findById(model.getModelId()).orElseThrow());
        offerEntity.setSeller(userRepository.findByEmail(userEmail()).orElseThrow());

        return offerEntity;
    }

    private String userEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            return currentUserEmail;
        }
        throw new NotFoundObjectException("No such user");
    }

    private OfferEntity setOffer(OfferEntity offer, OfferUpdateServiceModel serviceModel) {
        return (OfferEntity) offer.setPrice(serviceModel.getPrice())
                .setDescription(serviceModel.getDescription())
                .setMileage(serviceModel.getMileage())
                .setEngine(serviceModel.getEngine())
                .setTransmission(serviceModel.getTransmission())
                .setYear(serviceModel.getYear())
                .setImageUrl(serviceModel.getImageUrl())
                .setUpdated(Instant.now());
    }

    private OfferSummaryViewModel map(OfferEntity offer) {
        return modelMapper.map(offer, OfferSummaryViewModel.class)
                .setModel(offer.getModel().getName())
                .setBrand(offer.getModel().getBrand().getName())
                .setSeller(offer.getSeller().getFirstName() + " " + offer.getSeller().getLastName());
    }
}
