package com.example.demo.model.validation;


import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameExistValidator implements ConstraintValidator<UsernameExist, String> {
    private final UserService userService;

    @Autowired
    public UsernameExistValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.isUsernameFree(value);
    }
}
