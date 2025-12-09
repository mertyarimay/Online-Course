package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CancelUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersCoursesRequestModel;

import com.example.OnlineCourse.business.model.response.GetAllCoursesUsersResponse;
import com.example.OnlineCourse.business.model.response.GetAllUsersCoursesResponse;
import com.example.OnlineCourse.business.rules.CoursesRules;
import com.example.OnlineCourse.business.rules.UsersCoursesRules;
import com.example.OnlineCourse.business.rules.UsersRules;
import com.example.OnlineCourse.business.service.UsersCoursesService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.config.util.JwtUtil;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.usersCourses.UsersCoursesRepoJpa;
import com.example.OnlineCourse.entity.Courses;
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
    private final JwtUtil jwtUtil;


    @Override
    public CreateUsersCoursesRequestModel create(CreateUsersCoursesRequestModel createUsersCoursesRequestModell) {
        usersCoursesRules.userCheck(createUsersCoursesRequestModell.getUsersId(),createUsersCoursesRequestModell.getCoursesId());
        coursesRules.checkCoursesId(createUsersCoursesRequestModell.getCoursesId());
        UsersCourses usersCourses=modelMapperService.forRequest().map(createUsersCoursesRequestModell,UsersCourses.class);
        CreateUsersCoursesRequestModel createUserCourseRequestModel=modelMapperService.forRequest().map(usersCoursesRepoJpa.save(usersCourses),CreateUsersCoursesRequestModel.class);
        return createUserCourseRequestModel;
    }

    @Override
    public List<GetAllUsersCoursesResponse> getAll(Optional<Integer> usersId,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String usrId=jwtUtil.extractUserId(token);
        int userId=Integer.parseInt(usrId);
        if(usersId.isPresent()){
            if(userId==usersId.get()){
                usersRules.usersIdCheck(usersId.get());
                List<UsersCourses>usersCourses=usersCoursesRepoJpa.findByUsersId(usersId.get());
                List<GetAllUsersCoursesResponse>getAllUsersCoursesResponses=usersCourses.stream()
                        .map(userCourse -> modelMapperService.forResponse()
                                .map(userCourse, GetAllUsersCoursesResponse.class)).collect(Collectors.toList());
                return getAllUsersCoursesResponses;
            }
            return null;
        }
        return null;
    }

    @Override
    public List<GetAllCoursesUsersResponse> getAllUsers(Optional<Integer> coursesId,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String usrId=jwtUtil.extractUserId(token);
        int userId=Integer.parseInt(usrId);
        String role=jwtUtil.extractRole(token);
        Courses courses=coursesRepoJpa.findById(coursesId.get()).orElse(null);
        coursesRules.checkCoursesId(coursesId.get());
        if((coursesId.isPresent())&&(role.equals("ROLE_ADMIN"))){
            List<UsersCourses>usersCourses=usersCoursesRepoJpa.findByCoursesId(coursesId.get());
            List<GetAllCoursesUsersResponse>getAllCoursesUsersResponses=usersCourses.stream()
                    .map(userCourse -> modelMapperService.forResponse()
                            .map(userCourse, GetAllCoursesUsersResponse.class)).collect(Collectors.toList());
            return getAllCoursesUsersResponses;
        }
        else if((coursesId.isPresent())&&(userId==courses.getInstructor().getId())){
            List<UsersCourses>usersCourses=usersCoursesRepoJpa.findByCoursesId(coursesId.get());
            List<GetAllCoursesUsersResponse>getAllCoursesUsersResponses=usersCourses.stream()
                    .map(userCourse -> modelMapperService.forResponse()
                            .map(userCourse, GetAllCoursesUsersResponse.class)).collect(Collectors.toList());
            return getAllCoursesUsersResponses;
        }
        else {
            return null;
        }

    }

    @Override
    public boolean cancel(CancelUsersCoursesRequestModel cancelUsersCoursesRequestModel,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String usrId=jwtUtil.extractUserId(token);
        int userId=Integer.parseInt(usrId);
        if(userId==cancelUsersCoursesRequestModel.getUsersId()){
            int cancelCount=usersCoursesRepoJpa.cancelByCoursesIdAndUsersId(cancelUsersCoursesRequestModel.getUsersId(),cancelUsersCoursesRequestModel.getCoursesId());
            return cancelCount>0;
        }
        return false;
    }


}
