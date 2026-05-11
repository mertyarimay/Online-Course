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
import com.example.OnlineCourse.config.security.SecurityContextUser;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.dao.usersCourses.UsersCoursesRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Users;
import com.example.OnlineCourse.entity.UsersCourses;
import com.example.OnlineCourse.entity.UsersCoursesId;
import com.example.OnlineCourse.exception.ForbiddenException;
import com.example.OnlineCourse.exception.BadRequestException;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import com.example.OnlineCourse.exception.NotFoundException;
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
    private final UsersRepoJpa usersRepoJpa;
    private final UsersRules usersRules;
    private final CoursesRules coursesRules;
    private final UsersCoursesRules usersCoursesRules;


    @Override
    public CreateUsersCoursesRequestModel create(
            CreateUsersCoursesRequestModel createUsersCoursesRequestModell) {

        int currentUserId = SecurityContextUser.getCurrentUserId();
        if (currentUserId == createUsersCoursesRequestModell.getUsersId()) {

            usersCoursesRules.userCheck(
                    createUsersCoursesRequestModell.getUsersId(),
                    createUsersCoursesRequestModell.getCoursesId()
            );

            Users users = usersRepoJpa
                    .findById(createUsersCoursesRequestModell.getUsersId())
                    .orElseThrow(() ->
                            new NotFoundException(
                                    "Bu ID ye ait user kaydı mevcut değildir"
                            )
                    );

            Courses courses = coursesRepoJpa
                    .findById(createUsersCoursesRequestModell.getCoursesId())
                    .orElseThrow(() ->
                            new NotFoundException(
                                    "Bu ID ye ait course kaydı mevcut değildir"
                            )
                    );


            UsersCoursesId usersCoursesId = new UsersCoursesId();

            usersCoursesId.setUsersId(users.getId());
            usersCoursesId.setCoursesId(courses.getId());


            UsersCourses usersCourses = new UsersCourses();

            usersCourses.setId(usersCoursesId);
            usersCourses.setUsers(users);
            usersCourses.setCourses(courses);


            UsersCourses savedUsersCourses =
                    usersCoursesRepoJpa.save(usersCourses);


            CreateUsersCoursesRequestModel response =
                    modelMapperService.forResponse()
                            .map(savedUsersCourses,
                                    CreateUsersCoursesRequestModel.class);

            return response;

        } else {
            throw new ForbiddenException("Kursa Kayıt Başarısız");
        }
    }

    @Override
    public List<GetAllUsersCoursesResponse> getAll(Optional<Integer> usersId) {
        int userId = SecurityContextUser.getCurrentUserId();
        if(usersId.isEmpty()){
            throw new BadRequestException("Lütfen User Id yi giriniz");
        }
        if(userId!=usersId.get()){
            throw new ForbiddenException("Yetkisiz İşlem");
        }
        usersRules.usersIdCheck(usersId.get());
        List<UsersCourses>usersCourses=usersCoursesRepoJpa.findByUsersId(usersId.get());
        List<GetAllUsersCoursesResponse>getAllUsersCoursesResponses=usersCourses.stream()
                .map(userCourse -> modelMapperService.forResponse()
                        .map(userCourse, GetAllUsersCoursesResponse.class)).collect(Collectors.toList());
        return getAllUsersCoursesResponses;
    }

    @Override
    public List<GetAllCoursesUsersResponse> getAllUsers(Optional<Integer> coursesId) {
        if (coursesId.isEmpty()){
            throw new BadRequestException("Öğrenci Listesini görmek istediğiniz course seçiniz");
        }
        int userId = SecurityContextUser.getCurrentUserId();
        String role = SecurityContextUser.getCurrentRole();
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
            throw new ForbiddenException("Yetkisiz İşlem!!!");
        }

    }

    @Override
    public boolean cancel(CancelUsersCoursesRequestModel cancelUsersCoursesRequestModel) {
        int userId = SecurityContextUser.getCurrentUserId();
        if(userId==cancelUsersCoursesRequestModel.getUsersId()){
            int cancelCount=usersCoursesRepoJpa.cancelByCoursesIdAndUsersId(cancelUsersCoursesRequestModel.getUsersId(),cancelUsersCoursesRequestModel.getCoursesId());
            return cancelCount>0;
        }
        return false;
    }


}
