package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;

import java.util.List;

public interface InstructorService {
    CreateInstructorRequestModel create(CreateInstructorRequestModel createInstructorRequestModel);
    List<GetAllInstructorResponse> getAll();
    GetByIdInstructorResponse getById(int id);
    Boolean update(UpdateInstructorRequestModel updateInstructorRequestModel,int id);
    Boolean delete(int id);

}
