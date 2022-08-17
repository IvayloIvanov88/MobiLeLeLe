package com.example.demo.service.impl;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.model.service.UserRegisterServiceModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.security.CurrentUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CurrentUser currentUser, UserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.userRoleRepository = userRoleRepository;
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
    public void login(UserLoginServiceModel userLoginServiceModel) {
        Optional<UserEntity> optUser =
                userRepository.findByUsername(userLoginServiceModel.getUsername());

            UserEntity user = optUser.get();
            login(user);
            user.getUserRoles().forEach(r -> currentUser.addRole(r.getRole()));
        }


    @Override
    public void logOutCurrentUser() {
        currentUser.setAnonymous(true);
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
                .setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()))
                .setActive(true)
                .setUserRoles(List.of(userRole));

        newUser = userRepository.save(newUser);

        login(newUser);
    }

    @Override
    public boolean isUsernameFree(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        return optUser.isEmpty();
    }

    private void login(UserEntity user) {
        currentUser.setUserName(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setLoggedIn(true);
    }
    @Override
    public void loginUser(String userName) {
        UserEntity user = userRepository.findByUsername(userName).orElseThrow();
        List<UserRoleEnum> userRoles = user.
                getUserRoles().
                stream().
                map(UserRoleEntity::getRole).
                collect(Collectors.toList());

        currentUser.
                setAnonymous(false).
                setUserName(user.getUsername()).
                setUserRoles(userRoles);
    }
}
