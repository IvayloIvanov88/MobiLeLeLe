package com.example.demo.model.validation;

import com.example.demo.model.binding.UserLoginBindingModel;
import com.example.demo.model.service.UserLoginServiceModel;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<ValidPassword, Object> {

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public PasswordMatchValidator(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserLoginBindingModel loginModel = (UserLoginBindingModel) value;

        return userService.passwordsCheck(mapper.map(loginModel, UserLoginServiceModel.class));
    }
}
