package com.example.demo.service.impl;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.model.service.UserRegisterServiceModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final UserDetailsService appUserDetailsService;
    private final ModelMapper modelMapper;
    private final String adminPass;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService appUserDetailsService,
                           ModelMapper modelMapper, @Value("${app.default.admin.password}") String adminPass) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.modelMapper = modelMapper;
        this.adminPass = adminPass;
    }


    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setRole(UserRoleEnum.MODERATOR);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRoleEnum.USER);

            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);
            userRole = userRoleRepository.save(userRole);

            initAdmin(List.of(adminRole, moderatorRole));
            initModerator(List.of(moderatorRole));
            initUser(List.of(userRole));
        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity().
                setUserRoles(roles).
                setFirstName("Ivo").
                setLastName("Ivanov").
                setUsername("admin").
                setEmail("admin@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity().
                setUserRoles(roles).
                setFirstName("Moderator").
                setLastName("Moderatorov").
                setUsername("Moderator").
                setEmail("moderator@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRoleEntity> roles) {
        UserEntity user = new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setUsername("user").
                setEmail("user@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(user);
    }


    @Override
    public boolean authenticate(String userName, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(userName);

        if (userEntityOptional.isEmpty()) {
            return false;
        } else {
            return passwordEncoder.matches(password, userEntityOptional.get().getPassword());
        }
    }

    @Override
    public boolean passwordsCheck(UserLoginServiceModel loginServiceModel) {
        Optional<UserEntity> optUser =
                userRepository.findByUsername(loginServiceModel.getUsername());

        if (optUser.isEmpty()) return false;
        UserEntity user = optUser.get();

        return passwordEncoder.matches(loginServiceModel.getPassword(), user.getPassword());
    }

    @Override
    public void register(UserRegisterServiceModel userRegisterServiceModel) {

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

        UserEntity newUser = new UserEntity()
                .setFirstName(userRegisterServiceModel.getFirstName())
                .setLastName(userRegisterServiceModel.getLastName())
                .setUsername(userRegisterServiceModel.getUsername())
                .setEmail(userRegisterServiceModel.getEmail())
                .setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()))
                .setActive(true)
                .setUserRoles(List.of(userRole));

        userRepository.save(newUser);
//        login(newUser);

    }

    @Override
    public void login(UserLoginServiceModel userModel) {

        UserDetails userDetails =
                appUserDetailsService.loadUserByUsername(modelMapper.map(userModel, UserEntity.class).getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public boolean isUsernameFree(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        return optUser.isEmpty();
    }
}
