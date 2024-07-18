package com.example.OnlineCourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "birthDate")
    private Date birthDate;
    @Column(name = "department")
    private String department;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "instructor")
    private List<Courses>courses;
    @Column(name="password")
    private String password;

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
