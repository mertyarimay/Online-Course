package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.business.model.request.LoginAdminRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateAdminRequestModel;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.service.AdminService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.config.util.JwtUtil;
import com.example.OnlineCourse.dao.admin.AdminRepo;
import com.example.OnlineCourse.dao.admin.AdminRepoJpa;
import com.example.OnlineCourse.entity.Admin;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ModelMapperService modelMapperService;
    private final AdminRepo adminRepo;
    private final AdminRepoJpa adminRepoJpa;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public CreateAdminRequestModel create(CreateAdminRequestModel createAdminRequestModel) {
        Admin admin=modelMapperService.forRequest().map(createAdminRequestModel,Admin.class);
        admin.getRole().setId(createAdminRequestModel.getRoleId());
        adminRepo.create(admin);
        CreateAdminRequestModel createAdminModel=modelMapperService.forRequest().map(admin,CreateAdminRequestModel.class);
        return createAdminModel;
    }

    @Override
    public TokenModel login(LoginAdminRequestModel loginAdminRequestModel) {
        Admin admin=adminRepoJpa.findByUserName(loginAdminRequestModel.getUserName()).orElse(null);
        if (admin!=null&&passwordEncoder.matches(loginAdminRequestModel.getPassword(),admin.getPassword())){
            TokenModel tokenModel=new TokenModel();
            tokenModel.setUserId(String.valueOf(admin.getId()));
            tokenModel.setUserName(admin.getUserName());
            tokenModel.setRoleName(admin.getRole().getRoleName());
           return tokenModel;
       }else {
           return null;
        }

    }
    @Override
    public UpdateAdminRequestModel update(UpdateAdminRequestModel updateAdminRequestModel,int id,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String usrId=jwtUtil.extractUserId(token);
        int userId=Integer.parseInt(usrId);
        Admin admin=adminRepoJpa.findByUserName(updateAdminRequestModel.getUserName()).orElse(null);
        if((admin!=null)&&(userId==id)&&passwordEncoder.matches(updateAdminRequestModel.getOldPassword(),admin.getPassword())){
            admin.setPassword(passwordEncoder.encode(updateAdminRequestModel.getPassword()));
            adminRepoJpa.save(admin);
            UpdateAdminRequestModel updateAdminModel=modelMapperService.forRequest().map(admin,UpdateAdminRequestModel.class);
            return updateAdminModel;
        }
        else {
            return null;
        }
    }

}
