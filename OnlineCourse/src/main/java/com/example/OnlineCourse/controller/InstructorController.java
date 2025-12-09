package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateInstructorLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.service.InstructorService;
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
@RequestMapping("/instructor")

public class InstructorController {
    private final InstructorService instructorService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateInstructorRequestModel createInstructorRequestModel){
      CreateInstructorRequestModel createInstructorModel=instructorService.create(createInstructorRequestModel);
      if (createInstructorModel!=null){
          return ResponseEntity.ok(createInstructorModel);
      }else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt İşleminiz Başarısız Olmuştur.");
      }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<GetAllInstructorResponse>getAll(){
        List<GetAllInstructorResponse> getAllInstructorResponse=instructorService.getAll();
        return getAllInstructorResponse;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<Object>getById(@PathVariable("id")int id,@RequestHeader("Authorization") String token){
        GetByIdInstructorResponse getByIdInstructorResponse=instructorService.getById(id,token);
        if (getByIdInstructorResponse!=null){
           return ResponseEntity.ok(getByIdInstructorResponse);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu Id ye ait kayıt bulunamamıştır");
        }
    }

     @PutMapping("/update/{id}")
     @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
            public ResponseEntity<Object>update(@RequestBody @Valid UpdateInstructorRequestModel updateInstructorRequestModel,@PathVariable("id") int id,@RequestHeader("Authorization") String token){
            Boolean update=instructorService.update(updateInstructorRequestModel,id,token);
            if (update==true){
                return ResponseEntity.ok("Güncelleme İşlemi Başarılı Olmuştur.");

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Yetkiniz olmayan bir kayıtta işlem yapamazssınız!!!!");
            }

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object>delete(@PathVariable("id") int id,@RequestHeader("Authorization") String token){
        Boolean delete=instructorService.delete(id,token);
        if (delete==false){
          return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id+" "+"Numaralı Id nin silme işlemi BAŞARISIZ olmuşur");
        }else {
            return ResponseEntity.ok(id+" "+"Numaralı Id ye ait silme işlemi başarılı bir şekilde gerçekleşmiştir.");
        }
    }
    @PostMapping("/login")
    public String login(@RequestBody @Valid CreateInstructorLoginRequestModel createInstructorLoginRequestModel){
        TokenModel tokenModel =instructorService.instructorLogin(createInstructorLoginRequestModel);
        if (tokenModel != null) {
            return jwtUtil.generateToken(tokenModel.getUserName(),tokenModel.getUserId(),tokenModel.getRoleName());
        }else{
            String hata="Email veya şifreniz hatalı";
            return hata;
        }

    }



}
