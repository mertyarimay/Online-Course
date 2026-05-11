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
    public ResponseEntity<Object> create(@RequestBody @Valid CreateCoursesRequestModel createCoursesRequestModel) {
        CreateCoursesRequestModel createCourseRequestModel = coursesService.create(createCoursesRequestModel);
        return ResponseEntity.ok(createCourseRequestModel);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object> getAll(@RequestParam Optional<Integer> instructorId) {
        List<GetAllCoursesResponse> getAllCoursesResponses = coursesService.getAll(instructorId);
        return ResponseEntity.ok(getAllCoursesResponses);
    }


    @GetMapping("/getAll")
    public List<GetAllCoursesResponse> getAllCourseTypeId(@RequestParam Optional<Integer> courseTypeId) {
        List<GetAllCoursesResponse> getAllCoursesResponses = coursesService.getAllCourseTypeId(courseTypeId);
        return getAllCoursesResponses;

    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") int id) {
        GetByIdCoursesResponse getByIdCourseResponse = coursesService.getById(id);
        return ResponseEntity.ok(getByIdCourseResponse);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object> update(@RequestBody UpdateCoursesRequestModel updateCoursesRequestModel, @PathVariable("id") int id) {
        UpdateCoursesRequestModel updateCourseRequestModel = coursesService.update(updateCoursesRequestModel, id);
        return ResponseEntity.ok(updateCourseRequestModel);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Boolean delete = coursesService.delete(id);
        if (delete != false) {
            return ResponseEntity.ok("Kurs Kayıt Silme İşlemi Başarılı Bir Şekilde Gerçekleşti");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silme işlemi başarısız");
        }
    }
}
