package com.example.demo.service;

import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.model.service.UserRegisterServiceModel;


public interface UserService {
    /**
     * @param userName
     * @param password
     * @returns true if the user authenticated successfully.
     */
    boolean authenticate(String userName, String password);
    void login(UserLoginServiceModel userLoginServiceModel);

    void loginUser(String userName);

    void logOutCurrentUser();

    boolean passwordsCheck(UserLoginServiceModel loginServiceModel);

    void register(UserRegisterServiceModel userRegisterServiceModel);
    boolean isUsernameFree(String username);
    
}
