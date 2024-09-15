package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateAdminRequestModel;
import com.example.OnlineCourse.business.model.request.LoginAdminRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateAdminRequestModel;
import com.example.OnlineCourse.business.service.AdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateAdminRequestModel createAdminRequestModel){
        CreateAdminRequestModel createAdminModel=adminService.create(createAdminRequestModel);
    if (createAdminModel!=null){
       return ResponseEntity.ok("Kullanıcı adı ve şifre Başarılı bir şekilde Kayıt edildi.");
    }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt Başarısız");
    }
    }


    @PostMapping("/login")
    public ResponseEntity<Object>login(@RequestBody @Valid LoginAdminRequestModel loginAdminRequestModel){
        boolean loginAdminModel=adminService.login(loginAdminRequestModel);
        if(loginAdminModel==true){
            return ResponseEntity.ok("Login İşlemi Başarılı Bir Şekilde Gerçekleşti.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login İşlemi Başarısız Olmuştur");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateAdminRequestModel updateAdminRequestModel){
        UpdateAdminRequestModel updateAdminRequestModel1=adminService.update(updateAdminRequestModel);
        if(updateAdminRequestModel1!=null){
            return ResponseEntity.ok("Şifre Güncelleme İşlemi Başarılı Bir Şekilde Gerçekleşti.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Şifre Güncelleme İşlemi Başarısız");
    }
}
