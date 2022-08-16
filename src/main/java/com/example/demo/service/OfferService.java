package com.example.demo.service;

import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.service.OfferUpdateServiceModel;
import com.example.demo.model.view.OfferSummaryViewModel;

import java.util.List;

public interface OfferService {

    List<OfferSummaryViewModel> getAllOffers();

    long save(OfferServiceModel model);

    void delete(long id);

    void updateOffer(OfferUpdateServiceModel updateServiceModel);
}
