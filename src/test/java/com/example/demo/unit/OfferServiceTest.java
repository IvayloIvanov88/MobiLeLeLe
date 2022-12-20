package com.example.demo.unit;

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

import static org.mockito.Mockito.mock;
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
    private ModelMapper modelMapper;

    private UserRoleEntity userRoleAdmin;
    private UserEntity user;
    private BrandEntity brand;
    private ModelEntity modelTwo;
    private OfferEntity offerOne;
    private OfferEntity offerTwo;

    private OfferServiceModel offerModelOne;

    @BeforeEach
    public void setUp() {
        modelMapper = mock(ModelMapper.class);
        serviceToTest = new OfferServiceImpl(mockOfferRepository, mockUserRepository, mockModelRepository, new ModelMapper());

        userRoleAdmin = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);

        user = new UserEntity()
                .setUsername("user")
                .setFirstName("user")
                .setLastName("userov")
                .setUserRoles(List.of(userRoleAdmin)).setEmail("Email@Email");

        brand = new BrandEntity().setName("audiBrand");
        modelTwo = new ModelEntity().setBrand(brand).setName("Audi").setCategory(ModelCategoryEnum.CAR);

        offerOne = new OfferEntity();
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

        offerTwo = new OfferEntity();
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

        offerModelOne = new OfferServiceModel();
        offerModelOne.setMileage(1000);

    }

    @Test
    public void testGetAllOffersShouldReturnAllOffers() {
        //arrange
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
        when(mockOfferRepository.findById(1L)).thenReturn(Optional.of(offerOne));

        OfferSummaryViewModel actual = serviceToTest.getOfferById(1L);

        Assertions.assertEquals(offerOne.getPrice(), actual.getPrice());
        Assertions.assertEquals(offerOne.getSeller().getFirstName() + " " + offerOne.getSeller().getLastName()
                , actual.getSeller());
        Assertions.assertEquals(offerOne.getModel().getName(), actual.getModel());
        Assertions.assertEquals(offerOne.getEngine(), actual.getEngine());
        Assertions.assertEquals(offerOne.getId(), actual.getId());
    }

    @Test
    @WithMockUser(username = "Ivo@mail.bg", roles = {"ADMIN", "USER", "MODERATOR"})
    public void testSaveShouldSave() {

        when(mockOfferRepository.save(offerOne)).thenReturn(offerOne);
        when(mockUserRepository.findByEmail("Email@Email")).thenReturn(Optional.of(user));
        when(mockModelRepository.findById(modelTwo.getId())).thenReturn(Optional.of(modelTwo));

//        long actual = serviceToTest.save(modelMapper.map(offerOne, OfferServiceModel.class));
//        serviceToTest.save()
//        Assertions.assertEquals(1L, actual);
    }
}
