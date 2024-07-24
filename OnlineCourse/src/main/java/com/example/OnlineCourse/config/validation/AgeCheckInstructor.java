package com.example.OnlineCourse.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeCheckInstructorValidator.class)
public @interface AgeCheckInstructor {
    String message() default "kullanıcı en az 25 yaşında olmalıdır";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
