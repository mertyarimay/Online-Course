package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.courseTitle.CourseTitleRepoJpa;
import com.example.OnlineCourse.entity.CourseTitle;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseTitleRules {
    private final CourseTitleRepoJpa courseTitleRepoJpa;

    public void checkTitle(String title){
        if(courseTitleRepoJpa.existsByTitle(title)){
            throw new BusinessExcepiton("Bu başlıkta bir kayıt mevcuttur");
        }
    }


    public void checkTitleId(int id){
        CourseTitle courseTitle=courseTitleRepoJpa.findById(id).orElse(null);
        if(courseTitle==null){
            throw new BusinessExcepiton("Bu Idye ait Kurs Kaydı Mevcut Değildir.");
        }
    }

    }


