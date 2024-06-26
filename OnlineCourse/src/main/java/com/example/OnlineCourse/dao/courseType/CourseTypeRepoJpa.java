package com.example.OnlineCourse.dao.courseType;

import com.example.OnlineCourse.entity.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseTypeRepoJpa extends JpaRepository<CourseType,Integer> {
    Boolean existsByName(String name);

    List<CourseType>findByCourseTitleId(Integer courseTitleId);
}
