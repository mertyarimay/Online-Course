package com.example.OnlineCourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<Users>users;

    @OneToMany(mappedBy = "role")
    private List<Instructor>instructors;

    @OneToMany(mappedBy = "role")
    private List<Admin>admins;
}
