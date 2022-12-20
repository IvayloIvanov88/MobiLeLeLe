package com.example.demo.unit;


import com.example.demo.model.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserEntity testUser;
    private UserService userService;
    @Mock
    private UserRepository mockedUserRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @BeforeEach
    public void init() {
        String password = "1234";
        userService = new UserServiceImpl(
                mockedUserRepository, userRoleRepository, passwordEncoder, roleService, password);

        testUser = new UserEntity();
        testUser.setId(123L);
        testUser.setUsername("Random user");
        testUser.setEmail("random@example.com");
        testUser.setPassword(password);
        Mockito.when(mockedUserRepository.findByUsername("Random user"))
                .thenReturn(Optional.of(testUser));

    }

    @Test
    public void userServiceGetUserWithCorrectUsernameShouldReturnCorrect() {


        UserEntity expected = testUser;

        UserEntity actual = userService.getOrCreateUser("random@example.com");

        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    }
}
