package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTypeResponse;
import com.example.OnlineCourse.entity.CourseType;

import java.util.List;
import java.util.Optional;

public interface CourseTypeService {
    CreateCourseTypeRequestModel create(CreateCourseTypeRequestModel createCourseTypeRequestModel);
    List<GetAllCourseTypeResponse>getAll(Optional<Integer>courseTitleId);
}
