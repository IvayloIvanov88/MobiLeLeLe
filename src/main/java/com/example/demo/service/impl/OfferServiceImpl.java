package com.example.demo.service.impl;

import com.example.demo.model.entity.OfferEntity;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.service.OfferUpdateServiceModel;
import com.example.demo.model.view.OfferSummaryViewModel;
import com.example.demo.repository.ModelRepository;
import com.example.demo.repository.OfferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.OfferService;
import com.example.demo.web.NotFoundObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final CurrentUser currentUser;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(CurrentUser currentUser, OfferRepository offerRepository, UserRepository userRepository, ModelRepository modelRepository, ModelMapper modelMapper) {
        this.currentUser = currentUser;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OfferSummaryViewModel> getAllOffers() {
        //todo
        List<OfferSummaryViewModel> result = new ArrayList<>();
//        List<OfferEntity> allOffers = offerRepository.findAll();
//
//        allOffers.forEach(oe -> {
//            OfferEntity offerEntity = new
//
//        });

        return result;
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

        private OfferEntity asNewEntity(OfferServiceModel model) {
        OfferEntity offerEntity = new OfferEntity();
        modelMapper.map(model, offerEntity);
        offerEntity.setId(null);

        offerEntity.setModel(modelRepository.findById(model.getModelId()).orElseThrow());
        offerEntity.setSeller(userRepository.findByUsername(currentUser.getUserName()).orElseThrow());
        return offerEntity;
    }
}
