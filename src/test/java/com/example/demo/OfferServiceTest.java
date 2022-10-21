package com.example.demo;

import com.example.demo.model.entity.*;
import com.example.demo.model.entity.enums.EngineEnum;
import com.example.demo.model.entity.enums.ModelCategoryEnum;
import com.example.demo.model.entity.enums.TransmissionEnum;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.service.OfferServiceModel;
import com.example.demo.model.view.OfferSummaryViewModel;
import com.example.demo.repository.ModelRepository;
import com.example.demo.repository.OfferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.OfferServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    private OfferServiceImpl serviceToTest;

    @Mock
    private OfferRepository mockOfferRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ModelRepository mockModelRepository;
//    ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    public void setUp() {
//        modelMapper = mock(ModelMapper.class);
        serviceToTest = new OfferServiceImpl(mockOfferRepository, mockUserRepository, mockModelRepository, new ModelMapper());

    }

    @Test
    public void testGetAllOffersShouldReturnAllOffers() {
        //arrange
        UserRoleEntity userRoleAdmin = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
        UserEntity user = new UserEntity().setUsername("user").setUserRoles(List.of(userRoleAdmin)).setEmail("Email@Email");

        BrandEntity brand = new BrandEntity().setName("audiBrand");
        ModelEntity modelTwo = new ModelEntity().setBrand(brand).setName("Audi").setCategory(ModelCategoryEnum.CAR);

        OfferEntity offerOne = new OfferEntity();
        offerOne.
                setEngine(EngineEnum.GASOLINE).
                setPrice(BigDecimal.valueOf(1111)).
                setYear(1999).
                setSeller(user).
                setModel(modelTwo).
                setTransmission(TransmissionEnum.MANUAL).
                setUpdated(Instant.now()).
                setCreated(Instant.now()).
                setId(111L);

        OfferEntity offerTwo = new OfferEntity();
        offerTwo.
                setEngine(EngineEnum.DIESEL).
                setPrice(BigDecimal.valueOf(2222)).
                setYear(2022).
                setSeller(user).
                setModel(modelTwo).
                setTransmission(TransmissionEnum.AUTOMATIC).
                setUpdated(Instant.now()).
                setCreated(Instant.now()).
                setId(222L);

        when(mockOfferRepository.findAll()).thenReturn(List.of(offerOne, offerTwo));

        //act
        List<OfferSummaryViewModel> allOffers = serviceToTest.getAllOffers();

        OfferSummaryViewModel actualOne = allOffers.get(0);
        OfferSummaryViewModel actualTwo = allOffers.get(1);


        Assertions.assertEquals(2, allOffers.size());

        Assertions.assertEquals(offerOne.getYear(), actualOne.getYear());
        Assertions.assertEquals(offerOne.getUpdated(), actualOne.getUpdated());
        Assertions.assertEquals(offerOne.getCreated(), actualOne.getCreated());
        Assertions.assertEquals(offerOne.getId(), actualOne.getId());
        Assertions.assertEquals(offerOne.getPrice(), actualOne.getPrice());
        Assertions.assertEquals(offerOne.getTransmission(), actualOne.getTransmission());

        Assertions.assertEquals(offerTwo.getPrice(), actualTwo.getPrice());
        Assertions.assertEquals(offerTwo.getId(), actualTwo.getId());
        Assertions.assertEquals(offerTwo.getCreated(), actualTwo.getCreated());
        Assertions.assertEquals(offerTwo.getUpdated(), actualTwo.getUpdated());
        Assertions.assertEquals(offerTwo.getYear(), actualTwo.getYear());
        Assertions.assertEquals(offerTwo.getTransmission(), actualTwo.getTransmission());

    }

    @Test
    public void testGetOfferByIdShouldReturnRightOffer() {
        UserRoleEntity userRoleAdmin = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
        UserEntity user = new UserEntity()
                .setUsername("user")
                .setFirstName("user")
                .setLastName("userov")
                .setUserRoles(List.of(userRoleAdmin)).setEmail("Email@Email");

        BrandEntity brand = new BrandEntity().setName("audiBrand");
        ModelEntity modelTwo = new ModelEntity().setBrand(brand).setName("Audi").setCategory(ModelCategoryEnum.CAR);

        OfferEntity audiEntity = new OfferEntity()
                .setEngine(EngineEnum.GASOLINE)
                .setPrice(BigDecimal.valueOf(10))
                .setModel(modelTwo)
                .setSeller(user);
        audiEntity.setId(1L);

        when(mockOfferRepository.findById(1L)).thenReturn(Optional.of(audiEntity));

        OfferSummaryViewModel actual = serviceToTest.getOfferById(1L);

        Assertions.assertEquals(audiEntity.getPrice(), actual.getPrice());
        Assertions.assertEquals(audiEntity.getSeller().getFirstName() + " " + audiEntity.getSeller().getLastName()
                , actual.getSeller());
        Assertions.assertEquals(audiEntity.getModel().getName(), actual.getModel());
        Assertions.assertEquals(audiEntity.getEngine(), actual.getEngine());
        Assertions.assertEquals(audiEntity.getId(), actual.getId());
    }

//    @Test
    @WithMockUser(username = "Ivo@mail.bg", roles = {"ADMIN", "USER", "MODERATOR"})
    public void testSaveShouldSave(){

        UserRoleEntity userRoleAdmin = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
        UserEntity user = new UserEntity()
                .setUsername("user")
                .setFirstName("user")
                .setLastName("userov")
                .setUserRoles(List.of(userRoleAdmin)).setEmail("Email@Email");
        user.setId(1L);

        BrandEntity brand = new BrandEntity().setName("audiBrand");
        ModelEntity modelTwo = new ModelEntity().setBrand(brand).setName("Audi").setCategory(ModelCategoryEnum.CAR);
        modelTwo.setId(9L);

        OfferEntity audiEntity = new OfferEntity()
                .setEngine(EngineEnum.GASOLINE)
                .setPrice(BigDecimal.valueOf(10))
                .setModel(modelTwo)
                .setSeller(user);
        audiEntity.setId(1L);

        OfferServiceModel audiModel = new OfferServiceModel();
        audiModel.setModelId(9L);

        when(mockOfferRepository.save(audiEntity)).thenReturn(audiEntity);
        when(mockUserRepository.findByEmail("Email@Email")).thenReturn(Optional.of(user));
        when(mockModelRepository.findById(modelTwo.getId())).thenReturn(Optional.of(modelTwo));

        long actual = serviceToTest.save(audiModel);

        Assertions.assertEquals(1L,actual);
    }
}
