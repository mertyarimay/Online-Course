package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTypeResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCourseTypeResponse;
import com.example.OnlineCourse.business.service.CourseTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Course-Type")
@AllArgsConstructor

public class CourseTypeController {
    private final CourseTypeService courseTypeService;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateCourseTypeRequestModel createCourseTypeRequestModel){
        CreateCourseTypeRequestModel createCourseTypeRequestModel1=courseTypeService.create(createCourseTypeRequestModel);
        if(createCourseTypeRequestModel1!=null){
          return   ResponseEntity.ok("Kayıt İşleminiz Başarılı Bir Şekilde Gerçekleşti.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt İşleminiz Başarısız olmuştur.");
        }
    }

    @GetMapping
    public List<GetAllCourseTypeResponse> getAll(@RequestParam Optional<Integer>courseTitleId){
        List<GetAllCourseTypeResponse>getAllCourseTypeResponses=courseTypeService.getAll(courseTitleId);
          return getAllCourseTypeResponses;
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        GetByIdCourseTypeResponse getByIdCourseTypeResponse=courseTypeService.getById(id);
        if(getByIdCourseTypeResponse!=null){
         return ResponseEntity.ok(getByIdCourseTypeResponse);
        }
        else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu idye ait kayıt mevcut değildir");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody UpdateCourseTypeRequestModel updateCourseTypeRequestModel,@PathVariable("id") int id){
        UpdateCourseTypeRequestModel updateCourseTypeRequestModel1=courseTypeService.update(updateCourseTypeRequestModel,id);
        if(updateCourseTypeRequestModel1!=null){
            return ResponseEntity.ok(updateCourseTypeRequestModel1);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("BU ID YE AİT KAYIT BULUNAMAMIŞTIR");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id){
        Boolean delete=courseTypeService.delete(id);
        if (delete==true){
            return ResponseEntity.ok("Silme işlemi başarılı bir şekilde  gerçekleşti");
        }else{
         return    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id Geçersiz Silme İşlemi Başarısız!!!");
        }
    }





}
