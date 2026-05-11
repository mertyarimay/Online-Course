package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.business.model.request.LoginAdminRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateAdminRequestModel;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.service.AdminService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.config.security.SecurityContextUser;
import com.example.OnlineCourse.dao.admin.AdminRepoJpa;
import com.example.OnlineCourse.entity.Admin;
import com.example.OnlineCourse.exception.BadRequestException;
import com.example.OnlineCourse.exception.ForbiddenException;
import com.example.OnlineCourse.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final ModelMapperService modelMapperService;
    private final AdminRepoJpa adminRepoJpa;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CreateAdminRequestModel create(CreateAdminRequestModel createAdminRequestModel) {
        Admin admin=modelMapperService.forRequest().map(createAdminRequestModel,Admin.class);
        admin.getRole().setId(createAdminRequestModel.getRoleId());
        admin.setPassword(passwordEncoder.encode(createAdminRequestModel.getPassword()));
        adminRepoJpa.save(admin);
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
           throw new BadRequestException("Şifre ve ya Email Hatalı");
        }

    }
    @Override
    @Transactional
    public UpdateAdminRequestModel update(UpdateAdminRequestModel updateAdminRequestModel,int id) {
        int userId = SecurityContextUser.getCurrentUserId();
        Admin admin=adminRepoJpa.findByUserName(updateAdminRequestModel.getUserName())
                .orElseThrow(() -> new NotFoundException("Şifre Güncelleme İşlemi Başarısız"));
        if(userId!=id){
            throw new ForbiddenException("Şifre Güncelleme İşlemi Başarısız");
        }
        if(!passwordEncoder.matches(updateAdminRequestModel.getOldPassword(),admin.getPassword())){
            throw new BadRequestException("Şifre Güncelleme İşlemi Başarısız");
        }
        admin.setPassword(passwordEncoder.encode(updateAdminRequestModel.getPassword()));
        adminRepoJpa.save(admin);
        UpdateAdminRequestModel updateAdminModel=modelMapperService.forRequest().map(admin,UpdateAdminRequestModel.class);
        return updateAdminModel;
    }

}
