package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.UpdateCourseTitleRequestModel;
import com.example.OnlineCourse.business.service.CourseTitleService;
import com.example.OnlineCourse.business.model.request.CreateCourseTitleRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTitleResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCourseTitleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Course-Title")
@AllArgsConstructor


public class CourseTitleController {
    private final CourseTitleService courseTitleService;


    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateCourseTitleRequestModel createCourseTitleRequestModel){
   CreateCourseTitleRequestModel createCourseTitleModel=courseTitleService.create(createCourseTitleRequestModel);
   if(createCourseTitleModel!=null){
      return ResponseEntity.ok("Kayıt İşleminiz Başarılı Bir Şekilde Gerçekleşti.");
   }else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt İşleminiz Başarısız olmuştur.");
   }
    }


    @GetMapping("/getAll")
    public List<GetAllCourseTitleResponse>getAll(){
        List<GetAllCourseTitleResponse>getAllCourseTitleResponses=courseTitleService.getAll();
        return getAllCourseTitleResponses;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") int id){
        GetByIdCourseTitleResponse getByIdCourseTitleResponse=courseTitleService.getById(id);
        if(getByIdCourseTitleResponse==null){
         return    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aradığınız ıd de bir kayıt bulunamadı");
        }else {
          return   ResponseEntity.ok(getByIdCourseTitleResponse);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateCourseTitleRequestModel updateCourseTitleRequestModel, @PathVariable("id") int id){
       UpdateCourseTitleRequestModel updateCourseTitleModel=courseTitleService.update(updateCourseTitleRequestModel,id);
        if(updateCourseTitleModel!=null){
           return ResponseEntity.ok("Güncelleme işlemi Başarılı bir şekilde gerçekleşti.");
        }
        else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz ıd bulunamadı update işlemi başarısız");
        }

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id) {
        Boolean courseTitle = courseTitleService.delete(id);
        if (courseTitle == true) {
          return   ResponseEntity.ok("Silme işlemi başarılı olmuştur");
        } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id Bulunumadı Silme işlemi başarısız");
        }
    }
    }



