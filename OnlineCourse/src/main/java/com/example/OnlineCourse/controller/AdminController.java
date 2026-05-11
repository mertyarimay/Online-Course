package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.business.model.request.LoginAdminRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateAdminRequestModel;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.service.AdminService;
import com.example.OnlineCourse.config.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateAdminRequestModel createAdminRequestModel){
        CreateAdminRequestModel createAdminModel=adminService.create(createAdminRequestModel);
    if (createAdminModel!=null){
       return ResponseEntity.ok("Kullanıcı adı ve şifre Başarılı bir şekilde Kayıt edildi.");
    }else{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kayıt Başarısız");
    }
    }


    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginAdminRequestModel loginAdminRequestModel){
      TokenModel tokenModel =adminService.login(loginAdminRequestModel);
         if(tokenModel!=null){
             return jwtUtil.generateToken(tokenModel.getUserName(),tokenModel.getUserId(),tokenModel.getRoleName());
        }else {
            String hata="Şifre ve ya Email Hatalı";
            return hata;
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateAdminRequestModel updateAdminRequestModel,@PathVariable("id") int id){
        UpdateAdminRequestModel updateAdminRequestModel1=adminService.update(updateAdminRequestModel,id);
        if(updateAdminRequestModel1!=null){
            return ResponseEntity.ok("Şifre Güncelleme İşlemi Başarılı Bir Şekilde Gerçekleşti.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Şifre Güncelleme İşlemi Başarısız");
    }
}
