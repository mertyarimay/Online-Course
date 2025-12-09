package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateUsersLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateUsersRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllUsersResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCoursesResponse;
import com.example.OnlineCourse.business.model.response.GetByIdUsersResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
 CreateUsersRequestModel create(CreateUsersRequestModel createUsersRequestModel);
 List<GetAllUsersResponse>getAll();
 GetByIdUsersResponse getById(int id,String token);
 UpdateUsersRequestModel update(UpdateUsersRequestModel updateUsersRequestModel,int id,String token);
 Boolean delete(int id,String token);
 TokenModel authenticateUser(CreateUsersLoginRequestModel createUsersLoginRequestModel);
}
