package com.example.demo.model.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterServiceModel {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
