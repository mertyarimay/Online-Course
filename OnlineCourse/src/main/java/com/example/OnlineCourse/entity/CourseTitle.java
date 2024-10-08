package com.example.OnlineCourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CourseTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="title",unique = true)
    private String title;

    @OneToMany(mappedBy = "courseTitle")
    private List<CourseType>courseTypes;


}
