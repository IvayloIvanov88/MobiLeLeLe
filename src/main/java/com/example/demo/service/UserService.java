package com.example.demo.service;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.entity.enums.UserRoleEnum;
import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.model.service.UserRegisterServiceModel;

import java.util.List;


public interface UserService {

    void changeRole(String username, UserRoleEnum valueOf);

    List<String> findAllUsernames();

    boolean passwordsCheck(UserLoginServiceModel loginServiceModel);

    void register(UserRegisterServiceModel userRegisterServiceModel);
//    void registerAndLogin(UserRegisterServiceModel userRegisterServiceModel);
    boolean isUsernameFree(String username);

    UserEntity getOrCreateUser(String email);


    void init();
}
