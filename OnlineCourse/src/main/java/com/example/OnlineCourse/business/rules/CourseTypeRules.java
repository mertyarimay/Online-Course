package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.courseType.CourseTypeRepoJpa;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseTypeRules {
    private final CourseTypeRepoJpa courseTypeRepoJpa;

    public void checkName(String name){
       if(courseTypeRepoJpa.existsByName(name)){
           throw new BusinessExcepiton("Bu isimde kayıt mevcuttur");

       }


    }


}
