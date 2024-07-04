package com.example.OnlineCourse.controller;



import com.example.OnlineCourse.business.model.request.CreateCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCoursesResponse;
import com.example.OnlineCourse.business.service.CoursesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Courses")
@AllArgsConstructor

public class CoursesController {
    private final CoursesService coursesService;

    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateCoursesRequestModel createCoursesRequestModel){
        CreateCoursesRequestModel createCoursesRequestModel1=coursesService.create(createCoursesRequestModel);
        if (createCoursesRequestModel1!=null){
         return    ResponseEntity.ok("Kaydınız Başarılı bir şekilde oluşmuştur");
        }else {
         return    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kurs Kayıt İşleminiz Başarısız Olmuştur");
        }
    }
    @GetMapping
    public List<GetAllCoursesResponse>getAll(@RequestParam Optional<Integer>instructorId){
        List<GetAllCoursesResponse>getAllCoursesResponses=coursesService.getAll(instructorId);
        return getAllCoursesResponses;
    }


    @GetMapping("/getAll")
    public List<GetAllCoursesResponse>getAllCourseTypeId(@RequestParam Optional<Integer>courseTypeId){
        List<GetAllCoursesResponse>getAllCoursesResponses=coursesService.getAllCourseTypeId(courseTypeId);
        return getAllCoursesResponses;

    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        GetByIdCoursesResponse getByIdCoursesResponse=coursesService.getById(id);
        if(getByIdCoursesResponse!=null){
          return   ResponseEntity.ok(getByIdCoursesResponse);
        }else{
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Idye Ait Kayıt Bulunamamıştır");
        }
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<Object>update(@RequestBody UpdateCoursesRequestModel updateCoursesRequestModel,@PathVariable("id")int id){
        UpdateCoursesRequestModel updateCoursesRequestModel1=coursesService.update(updateCoursesRequestModel,id);
        if(updateCoursesRequestModel1!=null){
            return ResponseEntity.ok("Güncelleme işleminiz Başarılı bir şekilde Gerçekleşti");
        }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id Geçersiz Güncelleme İşlemi Başarısız");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id){
        Boolean delete=coursesService.delete(id);
        if(delete!=false){
            return ResponseEntity.ok(id+" "+"no lu  kaydınızın silme işlemi başarılı bir şekilde gerçekleşmiştir");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id+" "+"no lu kayıt bulunamadığı için silme işlemi başarısız");
        }
    }



}
