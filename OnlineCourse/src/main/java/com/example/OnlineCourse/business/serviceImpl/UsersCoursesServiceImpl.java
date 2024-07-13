package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateUsersCoursesRequestModel;

import com.example.OnlineCourse.business.model.response.GetAllCoursesUsersResponse;
import com.example.OnlineCourse.business.model.response.GetAllUsersCoursesResponse;
import com.example.OnlineCourse.business.rules.CoursesRules;
import com.example.OnlineCourse.business.rules.UsersCoursesRules;
import com.example.OnlineCourse.business.rules.UsersRules;
import com.example.OnlineCourse.business.service.UsersCoursesService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.usersCourses.UsersCoursesRepoJpa;
import com.example.OnlineCourse.entity.UsersCourses;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersCoursesServiceImpl implements UsersCoursesService {
    private final ModelMapperService modelMapperService;
    private final UsersCoursesRepoJpa usersCoursesRepoJpa;
    private final CoursesRepoJpa coursesRepoJpa;
    private final UsersRules usersRules;
    private final CoursesRules coursesRules;
    private final UsersCoursesRules usersCoursesRules;


    @Override
    public CreateUsersCoursesRequestModel create(CreateUsersCoursesRequestModel createUsersCoursesRequestModell) {
        usersCoursesRules.usersCheck(createUsersCoursesRequestModell.getUsersId(),createUsersCoursesRequestModell.getCoursesId());
        coursesRules.checkCoursesId(createUsersCoursesRequestModell.getCoursesId());
        UsersCourses usersCourses=modelMapperService.forRequest().map(createUsersCoursesRequestModell,UsersCourses.class);
        CreateUsersCoursesRequestModel createUsersCoursesRequestModel=modelMapperService.forRequest().map(usersCoursesRepoJpa.save(usersCourses),CreateUsersCoursesRequestModel.class);
        return createUsersCoursesRequestModel;
    }

    @Override
    public List<GetAllUsersCoursesResponse> getAll(Optional<Integer> usersId) {

        if(usersId.isPresent()){
            usersRules.usersIdCheck(usersId.get());
            List<UsersCourses>usersCourses=usersCoursesRepoJpa.findByUsersId(usersId.get());
            List<GetAllUsersCoursesResponse>getAllUsersCoursesResponses=usersCourses.stream()
                    .map(usersCourses1 -> modelMapperService.forResponse()
                            .map(usersCourses1, GetAllUsersCoursesResponse.class)).collect(Collectors.toList());
            return getAllUsersCoursesResponses;
        }else {
            return null;
        }
    }

    @Override
    public List<GetAllCoursesUsersResponse> getAllUsers(Optional<Integer> coursesId) {
        if(coursesId.isPresent()){
            coursesRules.checkCoursesId(coursesId.get());
            List<UsersCourses>usersCourses=usersCoursesRepoJpa.findByCoursesId(coursesId.get());
            List<GetAllCoursesUsersResponse>getAllCoursesUsersResponses=usersCourses.stream()
                    .map(usersCourses1 -> modelMapperService.forResponse()
                            .map(usersCourses1, GetAllCoursesUsersResponse.class)).collect(Collectors.toList());
            return getAllCoursesUsersResponses;
        }else {
            return null;
        }

    }


}
