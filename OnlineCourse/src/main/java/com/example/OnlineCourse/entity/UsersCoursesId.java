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
public class UsersCoursesId implements Serializable {
    @Column(name = "users_id")
    private int usersId;

    @Column(name = "courses_id")
    private int coursesId;

}
