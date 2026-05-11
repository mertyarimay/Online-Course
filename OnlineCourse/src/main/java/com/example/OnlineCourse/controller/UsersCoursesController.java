package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CancelUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesUsersResponse;
import com.example.OnlineCourse.business.model.response.GetAllUsersCoursesResponse;
import com.example.OnlineCourse.business.service.UsersCoursesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/users-courses")
public class UsersCoursesController {
    private final UsersCoursesService usersCoursesService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateUsersCoursesRequestModel createUsersCoursesRequestModel){
        CreateUsersCoursesRequestModel createUserCourseModel=usersCoursesService.create(createUsersCoursesRequestModel);
        if(createUserCourseModel!=null){
           return ResponseEntity.ok("Kurs Kayıt işleminiz başarılı bir şekilde oluşturulmuştur");
        }else {
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kurs Kayıt İşlemi Başarısız Olmuştur");
        }
    }

        @GetMapping
        @PreAuthorize("hasAuthority('ROLE_USER')")
        public ResponseEntity<?>getAll(@RequestParam Optional<Integer>usersId){
        List<GetAllUsersCoursesResponse>getAllUsersCoursesResponses=usersCoursesService.getAll(usersId);
        if(getAllUsersCoursesResponses!=null){
         return ResponseEntity.ok(getAllUsersCoursesResponses);
        }else {
         return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hatalı İşlem");
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    public ResponseEntity<?>getAllUsers(@RequestParam Optional<Integer>coursesId){
        List<GetAllCoursesUsersResponse>getAllCoursesUsersResponses=usersCoursesService.getAllUsers(coursesId);
       if(getAllCoursesUsersResponses!=null){
           return ResponseEntity.ok(getAllCoursesUsersResponses);
       }else {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("İşlem Başarısız!!!");
       }
    }
    @PostMapping("/cancel")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>cancel(@RequestBody CancelUsersCoursesRequestModel cancelUsersCoursesRequestModel){
      boolean cancel=usersCoursesService.cancel(cancelUsersCoursesRequestModel);
      if (cancel){
          return ResponseEntity.ok("Kursu İptal Etme İşleminiz Başarılı Bir Şekilde Gerçekleşti");

      }else {
          return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Kursu İptal Etme BAŞARISIZ Olmuştur");
      }
    }







}
