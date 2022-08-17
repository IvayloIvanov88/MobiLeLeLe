package com.example.demo.model.validation;


import com.example.demo.model.binding.UserRegisterBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatcher, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserRegisterBindingModel model = (UserRegisterBindingModel) value;
        return model.getPassword().equals(model.getConfirmPassword());
    }
}
