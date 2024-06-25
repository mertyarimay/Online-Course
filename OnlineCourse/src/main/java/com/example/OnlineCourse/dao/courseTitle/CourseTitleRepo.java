package com.example.OnlineCourse.dao.courseTitle;

import com.example.OnlineCourse.entity.CourseTitle;

import java.util.List;

public interface CourseTitleRepo {

    void create(CourseTitle courseTitle);
    List<CourseTitle>getAll();
    CourseTitle getById(int id);

}
