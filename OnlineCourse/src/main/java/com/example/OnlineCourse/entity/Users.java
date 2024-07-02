package com.example.OnlineCourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")


public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private  String lastName;
    @Column(name = "tckmlkNo")
    private String tckmlkNo;
    @ManyToOne
    @JoinColumn(name = "courses_id")
    private Courses courses;
    @OneToOne(mappedBy = "users")
    private Instructor instructor;
    @Column(name = "email")
    private String email;
    @Column(name="birthDate")
    private LocalDate birthDate;
}
