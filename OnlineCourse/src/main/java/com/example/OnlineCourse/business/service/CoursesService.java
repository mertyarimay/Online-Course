package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCoursesResponse;

import java.util.List;
import java.util.Optional;

public interface CoursesService {
    CreateCoursesRequestModel create(CreateCoursesRequestModel createCoursesRequestModel);
    List<GetAllCoursesResponse> getAll(Optional<Integer>instructorId);
    GetByIdCoursesResponse getById(int id);
    UpdateCoursesRequestModel update(UpdateCoursesRequestModel updateCoursesRequestModel,int id);
    Boolean delete(int id);

}
