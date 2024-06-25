package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.UpdateCourseTitleRequestModel;
import com.example.OnlineCourse.entity.CourseTitle;
import com.example.OnlineCourse.business.model.request.CreateCourseTitleRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTitleResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCourseTitleResponse;

import java.util.List;

public interface CourseTitleService {
    void create(CreateCourseTitleRequestModel createCourseTitleRequestModel);
    List<GetAllCourseTitleResponse>getAll();
    GetByIdCourseTitleResponse getById(int id);
    UpdateCourseTitleRequestModel update(UpdateCourseTitleRequestModel updateCourseTitleRequestModel, int id);
    Boolean delete(int id);
}
