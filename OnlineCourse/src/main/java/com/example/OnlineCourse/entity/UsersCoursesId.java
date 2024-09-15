package com.example.OnlineCourse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersCoursesId implements Serializable {  //bu sınıf usercourses ta bileşik anahtarı tanımlamımız için yaratıldı çift primery key olduğu için
    @Column(name = "users_id")
    private int usersId;

    @Column(name = "courses_id")
    private int coursesId;

}
