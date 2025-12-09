package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateInstructorLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;

import java.util.List;

public interface InstructorService {
    CreateInstructorRequestModel create(CreateInstructorRequestModel createInstructorRequestModel);
    List<GetAllInstructorResponse> getAll();
    GetByIdInstructorResponse getById(int id,String token);
    Boolean update(UpdateInstructorRequestModel updateInstructorRequestModel,int id,String token);
    Boolean delete(int id,String token);
    TokenModel instructorLogin(CreateInstructorLoginRequestModel createInstructorLoginRequestModel);

}
