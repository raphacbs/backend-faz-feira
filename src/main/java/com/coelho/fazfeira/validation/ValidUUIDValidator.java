package com.coelho.fazfeira.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ValidUUIDValidator implements ConstraintValidator<ValidUUID, String> {
    private boolean nullable; // Parâmetro nullable

    @Override
    public void initialize(ValidUUID constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return nullable;
        }
        try {
            UUID uuid = UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }
}

