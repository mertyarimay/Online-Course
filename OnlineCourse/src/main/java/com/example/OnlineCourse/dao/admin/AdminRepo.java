package com.example.OnlineCourse.dao.admin;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.entity.Admin;

public interface AdminRepo {
   Admin create(Admin admin);

}
