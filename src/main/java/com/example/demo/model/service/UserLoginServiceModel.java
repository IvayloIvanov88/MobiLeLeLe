package com.example.demo.model.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginServiceModel {
    @NotNull
    @Size(min = 2, max = 40 , message = "Username must be at least two characters")
    private String username;
    @NotNull
    @Size(min = 3, message = "Password is too short, it`s must be at least three characters")
    private String password;


    public String getUsername() {
        return username;
    }

    public UserLoginServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
