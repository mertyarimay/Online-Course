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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Course")
@AllArgsConstructor

public class CoursesController {
    private final CoursesService coursesService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateCoursesRequestModel createCoursesRequestModel,@RequestHeader("Authorization") String token){
        CreateCoursesRequestModel createCourseRequestModel=coursesService.create(createCoursesRequestModel,token);
        if (createCourseRequestModel!=null){
         return    ResponseEntity.ok("Kaydınız Başarılı bir şekilde oluşmuştur");
        }else {
         return    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kurs Kayıt İşleminiz Başarısız Olmuştur");
        }
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object>getAll(@RequestParam Optional<Integer>instructorId,@RequestHeader("Authorization") String token){
        List<GetAllCoursesResponse>getAllCoursesResponses=coursesService.getAll(instructorId,token);
        if(getAllCoursesResponses!=null){
            return ResponseEntity.ok(getAllCoursesResponses);

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hatalı işlem");
        }

    }


    @GetMapping("/getAll")
    public List<GetAllCoursesResponse>getAllCourseTypeId(@RequestParam Optional<Integer>courseTypeId){
        List<GetAllCoursesResponse>getAllCoursesResponses=coursesService.getAllCourseTypeId(courseTypeId);
        return getAllCoursesResponses;

    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        GetByIdCoursesResponse getByIdCourseResponse=coursesService.getById(id);
        if(getByIdCourseResponse!=null){
          return   ResponseEntity.ok(getByIdCourseResponse);
        }else{
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Idye Ait Kayıt Bulunamamıştır");
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public  ResponseEntity<Object>update(@RequestBody UpdateCoursesRequestModel updateCoursesRequestModel,@PathVariable("id")int id,@RequestHeader("Authorization") String token){
        UpdateCoursesRequestModel updateCourseRequestModel=coursesService.update(updateCoursesRequestModel,id,token);
        if(updateCourseRequestModel!=null){
            return ResponseEntity.ok("Güncelleme işleminiz Başarılı bir şekilde Gerçekleşti");
        }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Güncelleme işlemi başarısız");
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object>delete(@PathVariable("id") int id){
        Boolean delete=coursesService.delete(id);
        if(delete!=false){
            return ResponseEntity.ok(id+" "+"no lu  kaydınızın silme işlemi başarılı bir şekilde gerçekleşmiştir");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silme işlemi başarısız");
        }
    }



}
