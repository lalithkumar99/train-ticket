package com.booking.trainticketapplication.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class SectionValidator implements ConstraintValidator<ValidSection,String> {

    @Override
    public void initialize(ValidSection constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String section, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(section) && (section.equals("A") || section.equals("B"));
    }
}
