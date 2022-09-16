package com.example.demo.service;

import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.model.service.UserRegisterServiceModel;


public interface UserService {

    boolean authenticate(String userName, String password);
    void login(UserLoginServiceModel userLoginServiceModel);

    boolean passwordsCheck(UserLoginServiceModel loginServiceModel);

    void register(UserRegisterServiceModel userRegisterServiceModel);
    boolean isUsernameFree(String username);

    void init();
}
