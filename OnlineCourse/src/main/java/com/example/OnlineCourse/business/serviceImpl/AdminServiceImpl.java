package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.business.model.request.LoginAdminRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateAdminRequestModel;
import com.example.OnlineCourse.business.service.AdminService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
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

    @Override
    public CreateAdminRequestModel create(CreateAdminRequestModel createAdminRequestModel) {
        Admin admin=modelMapperService.forRequest().map(createAdminRequestModel,Admin.class);
        CreateAdminRequestModel createAdminRequestModel1=modelMapperService.forRequest().map(adminRepo.create(admin),CreateAdminRequestModel.class);
             return createAdminRequestModel1;
    }

    @Override
    public boolean login(LoginAdminRequestModel loginAdminRequestModel) {
        Admin admin=adminRepoJpa.findByKullaniciAdi(loginAdminRequestModel.getKullaniciAdi()).orElse(null);
        if (admin!=null&&passwordEncoder.matches(loginAdminRequestModel.getSifre(),admin.getSifre())){
            return true;
       }else {
            return false;
        }

    }

    @Override
    public UpdateAdminRequestModel update(UpdateAdminRequestModel updateAdminRequestModel) {
        Admin admin=adminRepoJpa.findByKullaniciAdi(updateAdminRequestModel.getKullaniciAdi()).orElse(null);
        if(admin!=null&&passwordEncoder.matches(updateAdminRequestModel.getEskiSifre(),admin.getSifre())){
            admin.setSifre(passwordEncoder.encode(updateAdminRequestModel.getSifre()));
            adminRepoJpa.save(admin);
            UpdateAdminRequestModel updateAdminRequestModel1=modelMapperService.forRequest().map(admin,UpdateAdminRequestModel.class);
            return updateAdminRequestModel1;
        }else {
            return null;
        }
    }

}
