package com.example.OnlineCourse.dao.courseTitle;

import com.example.OnlineCourse.entity.CourseTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CourseTitleRepoJpa extends JpaRepository<CourseTitle,Integer> {

    Boolean existsByTitle(String title);

}
