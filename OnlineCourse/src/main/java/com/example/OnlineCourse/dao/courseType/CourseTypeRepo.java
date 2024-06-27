package com.example.OnlineCourse.dao.courseType;

import com.example.OnlineCourse.entity.CourseType;

public interface CourseTypeRepo {

    CourseType getById(int id);
    Boolean delete(int id);

}
