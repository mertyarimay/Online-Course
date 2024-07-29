package com.example.OnlineCourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_courses")
@Data
public class UsersCourses {
    @EmbeddedId
    private UsersCoursesId id;

    @ManyToOne
    @MapsId("usersId") //bileşik anahtarların belirli özelliklerini  gösterir
    private Users users;

    @ManyToOne
    @MapsId("coursesId")
    private Courses courses;


    private LocalDate coursesRegistrationDate;

    @PrePersist
    public void prePersist() {
        this.coursesRegistrationDate = LocalDate.now();
    }




}
