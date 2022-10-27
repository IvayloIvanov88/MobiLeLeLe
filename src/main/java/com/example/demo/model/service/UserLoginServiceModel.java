package com.example.demo.model.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginServiceModel {
    @NotNull
    @Size(min = 2, max = 40 , message = "Username must be at least two characters")
    private String username;
    @NotNull
    @Size(min = 3, message = "Password is too short, it`s must be at least three characters")
    private String password;

    @NotNull
    @Email
    private String email;
}
