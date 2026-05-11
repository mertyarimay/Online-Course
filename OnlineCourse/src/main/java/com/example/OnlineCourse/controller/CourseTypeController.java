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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Course-Type")
@AllArgsConstructor

public class CourseTypeController {
    private final CourseTypeService courseTypeService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateCourseTypeRequestModel createCourseTypeRequestModel) {
        CreateCourseTypeRequestModel createCourseTypeModel = courseTypeService.create(createCourseTypeRequestModel);
        return ResponseEntity.ok(createCourseTypeModel);
    }

    @GetMapping
    public List<GetAllCourseTypeResponse> getAll(@RequestParam Optional<Integer> courseTitleId) {
        List<GetAllCourseTypeResponse> getAllCourseTypeResponses = courseTypeService.getAll(courseTitleId);
        return getAllCourseTypeResponses;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable("id") int id) {
        GetByIdCourseTypeResponse getByIdCourseTypeResponse = courseTypeService.getById(id);
        return ResponseEntity.ok(getByIdCourseTypeResponse);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> update(@RequestBody UpdateCourseTypeRequestModel updateCourseTypeRequestModel, @PathVariable("id") int id) {
        UpdateCourseTypeRequestModel updateCourseTypeModel = courseTypeService.update(updateCourseTypeRequestModel, id);
        return ResponseEntity.ok(updateCourseTypeModel);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Boolean delete = courseTypeService.delete(id);
        if (delete == true) {
            return ResponseEntity.ok("Silme işlemi başarılı bir şekilde  gerçekleşti");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silme İşlemi Başarısız!!!");
        }
    }


}
