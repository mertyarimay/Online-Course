package com.example.OnlineCourse.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})//bu annotasyonun hangi öğelerde kullanıcağını belirler
@Retention(RetentionPolicy.RUNTIME)//bu anotasyonun çalışma zamanında (runtime) erişilebilir olduğu belirtilir.
@Constraint(validatedBy = AgeCheckInstructorValidator.class)//hangi sınıfın doğrulayıcı olduğunu belirler
public @interface AgeCheckInstructor {
    String message() default "Eğitmen en az 25 yaşında olmalıdır"; //döndürülücek hata mesajını belirtir

    Class<?>[] groups() default {};//Bu, kısıtlamaların gruplar içinde organize edilmesini sağlar. Genellikle farklı doğrulama senaryoları için kullanılır. Burada boş bırakılmış, yani varsayılan grup kullanılacaktır.

    Class<? extends Payload>[] payload() default {};//ek veri taşıma amacıyla kullanılır


}
