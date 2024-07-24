package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.courseTitle.CourseTitleRepoJpa;
import com.example.OnlineCourse.dao.courseType.CourseTypeRepoJpa;
import com.example.OnlineCourse.entity.CourseTitle;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseTypeRules {
    private final CourseTypeRepoJpa courseTypeRepoJpa;
    private final CourseTitleRepoJpa courseTitleRepoJpa;

    public void checkName(String name){
       if(courseTypeRepoJpa.existsByName(name)){
           throw new BusinessExcepiton("Bu isimde kayıt mevcuttur");

       }
    }
    public  void checkCourseTitleId(Integer courseTitleId){
        CourseTitle courseTitle=courseTitleRepoJpa.findById(courseTitleId).orElse(null);
        if (courseTitle==null){
            throw new BusinessExcepiton("Course type kayıt ederken girdiğiniz Course Title Id mevcut değildir");
        }
    }


}
