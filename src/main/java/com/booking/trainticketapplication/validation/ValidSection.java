package com.booking.trainticketapplication.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { SectionValidator.class })
public @interface ValidSection {
    String message() default "Invalid section. Only A or B are allowed.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
