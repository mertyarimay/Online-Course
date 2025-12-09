package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateUsersLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateUsersRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllUsersResponse;
import com.example.OnlineCourse.business.model.response.GetByIdUsersResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.service.UsersService;
import com.example.OnlineCourse.config.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final JwtUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateUsersRequestModel createUsersRequestModel){
        CreateUsersRequestModel createUser=usersService.create(createUsersRequestModel);
        if(createUser!=null){
            return ResponseEntity.ok(createUser);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kayıt işleminiz BAŞARISIZ Olmuştur");
        }
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<GetAllUsersResponse>getAll(){
        List<GetAllUsersResponse>getAllUsersResponses=usersService.getAll();
        return getAllUsersResponses;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Object>getById(@PathVariable("id") int id,@RequestHeader("Authorization") String token){
        GetByIdUsersResponse getByIdUsersResponse=usersService.getById(id,token);
        if(getByIdUsersResponse!=null){
            return ResponseEntity.ok(getByIdUsersResponse);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("İşlem Başarısız!!!");
        }
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateUsersRequestModel updateUsersRequestModel,@PathVariable ("id") int id,@RequestHeader("Authorization") String token ){
        UpdateUsersRequestModel updateUser=usersService.update(updateUsersRequestModel,id,token);
        if (updateUser!=null){
          return   ResponseEntity.ok("Güncelleme işleminiz başarılı bir şekilde gerçekleşti.");
        }
        else {
          return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body("İşlem Başarısızz!!!");
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>delete(@PathVariable("id")int id,@RequestHeader("Authorization") String token){
        Boolean delete=usersService.delete(id,token);
        if (delete!=false){
          return   ResponseEntity.ok("Kaydınız Başarılı Bir Şekilde Silindi.");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Silme İşlemi Başarısız!!!");
        }
    }


   @PostMapping("/login")
    public String loginUser(@RequestBody @Valid CreateUsersLoginRequestModel createUsersLoginRequestModel){
        TokenModel tokenModel =usersService.authenticateUser(createUsersLoginRequestModel);
        if (tokenModel!=null){
            return jwtUtil.generateToken(tokenModel.getUserName(),tokenModel.getUserId(),tokenModel.getRoleName());
        }else {
            String hata="Geçersiz Email veya Şifre Girdiniz";
            return hata;

        }

       }
    }
