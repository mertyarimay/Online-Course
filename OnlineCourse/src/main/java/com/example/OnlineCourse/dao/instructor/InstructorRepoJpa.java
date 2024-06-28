package com.example.OnlineCourse.dao.instructor;

import com.example.OnlineCourse.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepoJpa extends JpaRepository<Instructor,Integer> {

    Boolean existsByEmail(String email);
}
