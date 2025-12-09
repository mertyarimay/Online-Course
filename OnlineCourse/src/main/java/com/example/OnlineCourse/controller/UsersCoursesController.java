package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CancelUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesUsersResponse;
import com.example.OnlineCourse.business.model.response.GetAllUsersCoursesResponse;
import com.example.OnlineCourse.business.service.UsersCoursesService;
import com.example.OnlineCourse.config.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>create(@RequestBody CreateUsersCoursesRequestModel createUsersCoursesRequestModel ,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ", "");
        String tokenUserId = jwtUtil.extractUserId(token);
        if(!tokenUserId.equals(String.valueOf(createUsersCoursesRequestModel.getUsersId()))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Yetkisiz İşlem");
        }
        CreateUsersCoursesRequestModel createUserCourseModel=usersCoursesService.create(createUsersCoursesRequestModel);
        if(createUserCourseModel!=null){
           return ResponseEntity.ok("Kurs Kayıt işleminiz başarılı bir şekilde oluşturulmuştur");
        }else {
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kurs Kayıt İşlemi Başarısız Olmuştur");
        }
    }

        @GetMapping
        @PreAuthorize("hasAuthority('ROLE_USER')")
        public ResponseEntity<?>getAll(@RequestParam Optional<Integer>usersId,@RequestHeader("Authorization") String token){
        List<GetAllUsersCoursesResponse>getAllUsersCoursesResponses=usersCoursesService.getAll(usersId,token);
        if(getAllUsersCoursesResponses!=null){
         return ResponseEntity.ok(getAllUsersCoursesResponses);
        }else {
         return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Yetkisiz İşlem");
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    public ResponseEntity<?>getAllUsers(@RequestParam Optional<Integer>coursesId,@RequestHeader("Authorization") String token){
        List<GetAllCoursesUsersResponse>getAllCoursesUsersResponses=usersCoursesService.getAllUsers(coursesId,token);
       if(getAllCoursesUsersResponses!=null){
           return ResponseEntity.ok(getAllCoursesUsersResponses);
       }else {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Yetkisiz İşlem!!!");
       }
    }
    @PostMapping("/cancel")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>cancel(@RequestBody CancelUsersCoursesRequestModel cancelUsersCoursesRequestModel,@RequestHeader("Authorization") String token){
      boolean cancel=usersCoursesService.cancel(cancelUsersCoursesRequestModel,token);
      if (cancel){
          return ResponseEntity.ok("Kursu İptal Etme İşleminiz Başarılı Bir Şekilde Gerçekleşti");

      }else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kursu İptal Etme BAŞARISIZ Olmuştur");
      }
    }







}
