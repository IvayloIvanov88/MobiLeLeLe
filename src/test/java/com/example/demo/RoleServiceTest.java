package com.example.demo;

import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.entity.service.RoleServiceModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    public RoleServiceImpl serviceToTest;

    @Mock
    private RoleRepository mockRoleRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = mock(ModelMapper.class);
        serviceToTest = new RoleServiceImpl(this.mockRoleRepository, modelMapper);
    }

//    @Test
    public void testFindByName() {

        UserRoleEntity admin = new UserRoleEntity().setId(1L).setRole(UserRoleEnum.ADMIN);

        RoleServiceModel roleAdmin = new RoleServiceModel();
        roleAdmin.setId(1);
        roleAdmin.setRole(admin);

        when(mockRoleRepository.findByRole(admin.getRole())).thenReturn(admin);

        RoleServiceModel actual = serviceToTest.findByName(admin.getRole());

        Assertions.assertEquals(roleAdmin.getRole(),actual.getRole());
    }

}
