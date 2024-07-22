package com.example.OnlineCourse.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeLimitValidator implements ConstraintValidator <AgeLimit, LocalDate> {
    private static final int MIN_AGE = 15;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        Period period = birthDate.until(today);
        int age = period.getYears();

        return age >= MIN_AGE;
    }

}
