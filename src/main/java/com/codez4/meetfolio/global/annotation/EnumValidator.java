package com.codez4.meetfolio.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid enums;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.enums = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;
        Enum<?>[] enumValues = this.enums.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || this.enums.ignoreCase()
                        && value.equalsIgnoreCase(enumValue.toString())) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}