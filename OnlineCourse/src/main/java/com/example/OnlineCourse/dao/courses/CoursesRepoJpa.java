package com.example.OnlineCourse.dao.courses;

import com.example.OnlineCourse.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursesRepoJpa extends JpaRepository<Courses,Integer> {
  List<Courses>findByInstructorId(Integer instructorId);

}
