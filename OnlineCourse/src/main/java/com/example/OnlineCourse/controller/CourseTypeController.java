package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTypeResponse;
import com.example.OnlineCourse.business.service.CourseTypeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Course-Type")
@AllArgsConstructor

public class CourseTypeController {
    private final CourseTypeService courseTypeService;
    @PostMapping("/create")
    public CreateCourseTypeRequestModel create(@RequestBody @Valid CreateCourseTypeRequestModel createCourseTypeRequestModel){
        CreateCourseTypeRequestModel createCourseTypeRequestModel1=courseTypeService.create(createCourseTypeRequestModel);
        return createCourseTypeRequestModel1;
    }
    @GetMapping
    public List<GetAllCourseTypeResponse> getAll(@RequestParam Optional<Integer>courseTitleId){
        List<GetAllCourseTypeResponse>getAllCourseTypeResponses=courseTypeService.getAll(courseTitleId);
          return getAllCourseTypeResponses;
    }



}
