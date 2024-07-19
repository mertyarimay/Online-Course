package com.example.OnlineCourse.business.service;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.business.model.request.LoginAdminRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateAdminRequestModel;
import com.example.OnlineCourse.entity.Admin;

public interface AdminService  {
    CreateAdminRequestModel create(CreateAdminRequestModel createAdminRequestModel);
    boolean login(LoginAdminRequestModel loginAdminRequestModel);

    UpdateAdminRequestModel update(UpdateAdminRequestModel updateAdminRequestModel);

}
