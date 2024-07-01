package com.example.OnlineCourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "course_type_id")
    private CourseType courseType;
    @Column(name = "description")
    private String description;
    @Column(name = "priceTl")
    private double price;
    @Column(name = "publishedAt")
    private LocalDate publishedAt;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @PrePersist
    public void prePersist() {
        this.publishedAt = LocalDate.now();
    }

}


