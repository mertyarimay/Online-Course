package com.example.OnlineCourse.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCheckInstructorValidator implements ConstraintValidator<AgeCheckInstructor, Date> {
    private static final int MIN_AGE = 25;

    @Override
    public void initialize(AgeCheckInstructor constraintAnnotation) { //kısıtlamanın başlatılması  çağrılır
    }

    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return false;
        }

        // Date'i LocalDate'e dönüştürme
        LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate today = LocalDate.now();
        Period period = Period.between(localBirthDate, today);
        int age = period.getYears();

        return age >= MIN_AGE;
    }


}
