package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;


    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateUsersRequestModel createUsersRequestModel){
        CreateUsersRequestModel createUsersRequestModel1=usersService.create(createUsersRequestModel);
        if(createUsersRequestModel1!=null){
            return ResponseEntity.ok("Kayıt İşleminiz Başarılı bir Şekilde Gerçekleşmiştir.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt işleminiz BAŞARISIZ Olmuştur");
        }
    }
}
