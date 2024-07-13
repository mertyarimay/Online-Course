package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoursesRules {
    private final CoursesRepoJpa coursesRepoJpa;


    public Courses checkPrice(Courses courses,int id) {
        if (coursesRepoJpa.checkPrice(id,courses.getPrice()).isPresent()){
           throw new BusinessExcepiton("Fiyat bir önceki fiyatınız ile aynı güncelleme yapamazssınız.");
        }else{
            return courses;
        }

    }
    public void checkCoursesId(Integer coursesId){
        Courses courses=coursesRepoJpa.findById(coursesId).orElse(null);
        if(courses==null){
            throw new BusinessExcepiton("Bu ID ye Ait course Kaydı Mevcut değildir");
        }
    }
}
