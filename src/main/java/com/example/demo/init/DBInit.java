package com.example.demo.init;

import com.example.demo.model.entity.BrandEntity;
import com.example.demo.model.entity.ModelEntity;
import com.example.demo.model.entity.OfferEntity;
import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.ModelCategoryEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DBInit implements CommandLineRunner {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;


    @Autowired
    public DBInit(ModelRepository modelRepository, BrandRepository brandRepository, OfferRepository offerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, UserService userService) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.init();
//
//        BrandEntity fordBrand = new BrandEntity();
//        fordBrand.setName("Ford");
//
//        BrandEntity audiBrand = new BrandEntity();
//        audiBrand.setName("Audi");
//
//        BrandEntity vwBrand = new BrandEntity();
//        vwBrand.setName("VW");
//
//        BrandEntity porscheBrand = new BrandEntity();
//        porscheBrand.setName("Porsche");
//
//        brandRepository.saveAll(List.of(porscheBrand, fordBrand, audiBrand, vwBrand));
//
//        initFiesta(fordBrand);
//        initA4(audiBrand);
//        initEtron(audiBrand);
//        initGolf5(vwBrand);
//        initTaycan(porscheBrand);
//
//        createFiestaOffer(initFiesta(fordBrand));

//        initUsers();


    }

    private void createFiestaOffer(ModelEntity modelEntity) {
        OfferEntity fiestaOffer = new OfferEntity();

        fiestaOffer.
                setEngine(EngineEnum.GASOLINE).
                setImageUrl("https://www.autoscout24.bg/assets/auto/images/model/ford/ford-fiesta/ford-fiesta-l-01.webp")
                .setMileage(160023)
                .setPrice(BigDecimal.valueOf(7000))
                .setYear(2007)
                .setDescription("Карана е от немска баба, бастуна седи още в багажника, само до църквата е ходила всяка неделя.")
                .setTransmission(TransmissionEnum.AUTOMATIC)
                .setModel(modelEntity)
                .setSeller(userRepository.findByUsername("admin").orElse(null));

        offerRepository.save(fiestaOffer);
    }

    private ModelEntity initA4(BrandEntity AudiBrand) {
        ModelEntity a4 = new ModelEntity();

        a4.
                setName("A4")
                .setCategory(ModelCategoryEnum.CAR)
                .setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR15UKvdq4sqxLoq8ejftLHvIaZrYsvWtc4sQ&usqp=CAU")
                .setStartYear(2005)
                .setBrand(AudiBrand);
        return modelRepository.save(a4);
    }

    private ModelEntity initGolf5(BrandEntity vwBrand) {
        ModelEntity golf5 = new ModelEntity();

        golf5.
                setName("Golf 5")
                .setCategory(ModelCategoryEnum.CAR)
                .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/c/ca/VW_Golf_V_front_20081127.jpg")
                .setStartYear(2004)
                .setBrand(vwBrand);
        return modelRepository.save(golf5);
    }

    private ModelEntity initTaycan(BrandEntity porscheBrand) {
        ModelEntity taycan = new ModelEntity();

        taycan
                .setName("Taycan")
                .setCategory(ModelCategoryEnum.CAR)
                .setStartYear(2020)
                .setBrand(porscheBrand)
                .setImageUrl("https://cdn4.focus.bg/fakti/photos/big/e02/porsche-taycan-761-kona-za-191-hiladi-1.jpg");
        return modelRepository.save(taycan);

    }

    private ModelEntity initEtron(BrandEntity AudiBrand) {
        ModelEntity eTron = new ModelEntity();

        eTron.
                setName("E-tron")
                .setCategory(ModelCategoryEnum.CAR)
                .setImageUrl("https://www.autoclub.bg/wp-content/uploads/2021/02/audi-rs-e-tron-gt.jpg")
                .setStartYear(2015)
                .setBrand(AudiBrand);
        return modelRepository.save(eTron);
    }

    private ModelEntity initFiesta(BrandEntity fordBrand) {
        ModelEntity fiesta = new ModelEntity();

        fiesta.
                setName("Fiesta")
                .setCategory(ModelCategoryEnum.CAR)
                .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/0/05/2005_Ford_Fiesta_%28WP%29_Ghia_5-door_hatchback_%282015-07-24%29_01.jpg")
                .setStartYear(1976)
                .setBrand(fordBrand);
        return modelRepository.save(fiesta);
    }
}
