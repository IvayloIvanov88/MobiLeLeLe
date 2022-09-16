package com.example.demo.model.binding;


import com.example.demo.model.validation.PasswordMatcher;
import com.example.demo.model.validation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@PasswordMatcher
public class UserRegisterBindingModel {

    @NotNull
    @Size(min = 4, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 4, max = 20)
    private String lastName;

    @NotNull
    @Size(min = 4, max = 20)
    @UniqueUsername
    private String username;

    @NotNull
    @Email
    @Size(min = 3, message ="Email is too short")
    private String email;
    @NotNull
    @Size(min = 4, max = 20)
    private String password;

    @NotNull
    @Size(min = 4, max = 20)
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
