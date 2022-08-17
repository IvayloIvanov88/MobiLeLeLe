package com.example.demo.model.binding;

import com.example.demo.model.validation.UsernameExist;
import com.example.demo.model.validation.ValidPassword;

@ValidPassword
public class UserLoginBindingModel {

    @UsernameExist
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public UserLoginBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
