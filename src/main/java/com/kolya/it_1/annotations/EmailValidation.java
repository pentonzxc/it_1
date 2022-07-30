package com.kolya.it_1.annotations;

import com.kolya.it_1.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<EmailNotExist, String> {
    public final UserService userService;

    public EmailValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.findUserByEmail(email).isPresent();
    }

}
