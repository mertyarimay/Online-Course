package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.entity.Users;

public interface UsersService {
 CreateUsersRequestModel create(CreateUsersRequestModel createUsersRequestModel);
}
