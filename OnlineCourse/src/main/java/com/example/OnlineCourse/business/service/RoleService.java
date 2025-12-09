package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateRoleModel;
import com.example.OnlineCourse.entity.Role;

public interface RoleService {
    CreateRoleModel create(CreateRoleModel createRoleModel);
}
