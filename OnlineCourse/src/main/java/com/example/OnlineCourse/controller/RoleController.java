package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateRoleModel;
import com.example.OnlineCourse.business.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {
    private  final RoleService roleService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object>create(@RequestBody CreateRoleModel createRoleModel){
        CreateRoleModel model=roleService.create(createRoleModel);
        if(model!=null){
            return ResponseEntity.ok("Role kayıt işlemi başarılı bir şekilde oluştu");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kayıt İşlemi Başarısız");
        }
    }

}
