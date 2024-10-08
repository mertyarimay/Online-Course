package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateInstructorLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.service.InstructorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/instructor")

public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateInstructorRequestModel createInstructorRequestModel){
      CreateInstructorRequestModel createInstructorModel=instructorService.create(createInstructorRequestModel);
      if (createInstructorModel!=null){
          return ResponseEntity.ok("Kayıt işleminiz Başarılı bir şekilde gerçekleşti.");
      }else {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt İşleminiz Başarısız Olmuştur.");
      }
    }

    @GetMapping("/getAll")
    public List<GetAllInstructorResponse>getAll(){
        List<GetAllInstructorResponse> getAllInstructorResponse=instructorService.getAll();
        return getAllInstructorResponse;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id")int id){
        GetByIdInstructorResponse getByIdInstructorResponse=instructorService.getById(id);
        if (getByIdInstructorResponse!=null){
           return ResponseEntity.ok(getByIdInstructorResponse);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu Id ye ait kayıt bulunamamıştır");
        }
    }

     @PutMapping("/update/{id}")
            public ResponseEntity<Object>update(@RequestBody @Valid UpdateInstructorRequestModel updateInstructorRequestModel,@PathVariable("id") int id){
            Boolean update=instructorService.update(updateInstructorRequestModel,id);
            if (update==true){
                return ResponseEntity.ok("Güncelleme İşlemi Başarılı Olmuştur.");

            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Güncelleme işlemi BAŞARISIZ olmuştur");
            }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id){
        Boolean delete=instructorService.delete(id);
        if (delete==false){
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body(id+" "+"Numaralı Id nin silme işlemi BAŞARISIZ olmuşur");
        }else {
            return ResponseEntity.ok(id+" "+"Numaralı Id ye ait silme işlemi başarılı bir şekilde gerçekleşmiştir.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Object>login(@RequestBody @Valid CreateInstructorLoginRequestModel createInstructorLoginRequestModel){
        Boolean login=instructorService.instructorLogin(createInstructorLoginRequestModel);
        if (login==true){
            return ResponseEntity.ok("Login işleminiz Başarılı Olmuştur");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login işlemi BAŞARISIZ olmuştur");
        }
    }



}
