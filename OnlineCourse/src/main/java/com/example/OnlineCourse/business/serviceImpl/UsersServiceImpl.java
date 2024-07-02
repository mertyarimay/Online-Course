package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.rules.UsersRules;
import com.example.OnlineCourse.business.service.UsersService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepoJpa usersRepoJpa;
    private final ModelMapperService modelMapperService;
    private final UsersRules usersRules;

    @Override
    public CreateUsersRequestModel create(CreateUsersRequestModel createUsersRequestModel) {
        usersRules.existByTckmlkNo(createUsersRequestModel.getTckmlkNo());
        Users users=modelMapperService.forRequest().map(createUsersRequestModel,Users.class);
        CreateUsersRequestModel createUsersRequestModel1=modelMapperService.forRequest().map(usersRepoJpa.save(users),CreateUsersRequestModel.class);
        return createUsersRequestModel1;
    }
}
