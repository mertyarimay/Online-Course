package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateUsersRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllUsersResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCoursesResponse;
import com.example.OnlineCourse.business.model.response.GetByIdUsersResponse;
import com.example.OnlineCourse.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
 CreateUsersRequestModel create(CreateUsersRequestModel createUsersRequestModel);
 List<GetAllUsersResponse>getAll();
 GetByIdUsersResponse getById(int id);
 UpdateUsersRequestModel update(UpdateUsersRequestModel updateUsersRequestModel,int id);
 Boolean delete(int id);
}
