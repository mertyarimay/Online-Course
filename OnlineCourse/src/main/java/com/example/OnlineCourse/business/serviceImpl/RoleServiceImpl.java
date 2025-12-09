package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateRoleModel;
import com.example.OnlineCourse.business.service.RoleService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.role.RoleRepoJpa;
import com.example.OnlineCourse.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final ModelMapperService modelMapperService;
    private final RoleRepoJpa roleRepoJpa;
    @Override
    public CreateRoleModel create(CreateRoleModel createRoleModel) {
        Role role=modelMapperService.forRequest().map(createRoleModel,Role.class);
        roleRepoJpa.save(role);
        CreateRoleModel model=modelMapperService.forRequest().map(role,CreateRoleModel.class);
        return  model;


    }
}
