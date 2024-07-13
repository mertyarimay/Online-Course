package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesUsersResponse;
import com.example.OnlineCourse.business.model.response.GetAllUsersCoursesResponse;
import com.example.OnlineCourse.business.service.UsersCoursesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/users-courses")
public class UsersCoursesController {
    private final UsersCoursesService usersCoursesService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody CreateUsersCoursesRequestModel createUsersCoursesRequestModel){
        CreateUsersCoursesRequestModel createUsersCoursesRequestModel1=usersCoursesService.create(createUsersCoursesRequestModel);
        if(createUsersCoursesRequestModel1!=null){
           return ResponseEntity.ok("Kurs Kayıt işleminiz başarılı bir şekilde oluşturulmuştur");
        }else {
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kurs Kayıt İşlemi Başarısız Olmuştur");
        }
    }

        @GetMapping
    public ResponseEntity<?>getAll(@RequestParam Optional<Integer>usersId){
        List<GetAllUsersCoursesResponse>getAllUsersCoursesResponses=usersCoursesService.getAll(usersId);
        if(getAllUsersCoursesResponses!=null){
         return ResponseEntity.ok(getAllUsersCoursesResponses);
        }else {
         return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lütfen Kullanıcı Giriniz");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?>getAllUsers(@RequestParam Optional<Integer>coursesId){
        List<GetAllCoursesUsersResponse>getAllCoursesUsersResponses=usersCoursesService.getAllUsers(coursesId);
       if(getAllCoursesUsersResponses!=null){
           return ResponseEntity.ok(getAllCoursesUsersResponses);
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lütfen Öğreci Listesini Görmek istediğiniz Kurs Id si Girin.");
       }
    }







}
