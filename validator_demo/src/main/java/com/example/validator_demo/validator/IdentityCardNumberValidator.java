package com.example.validator_demo.validator;

import com.example.validator_demo.annotation.IdentityCardNumber;
import com.example.validator_demo.utils.IdCardUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return IdCardUtils.isIDNumber(value.toString());
    }
}
