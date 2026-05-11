package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CancelUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesUsersResponse;
import com.example.OnlineCourse.business.model.response.GetAllUsersCoursesResponse;

import java.util.List;
import java.util.Optional;


public interface UsersCoursesService {
CreateUsersCoursesRequestModel create(CreateUsersCoursesRequestModel createUsersCoursesRequestModel);
List<GetAllUsersCoursesResponse> getAll(Optional<Integer>usersId);
List<GetAllCoursesUsersResponse>getAllUsers(Optional<Integer>coursesId);
boolean cancel(CancelUsersCoursesRequestModel cancelUsersCoursesRequestModel);
}
