package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.courseType.CourseTypeRepoJpa;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.entity.CourseType;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Instructor;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoursesRules {
    private final CoursesRepoJpa coursesRepoJpa;
    private final InstructorRepoJpa instructorRepoJpa;
    private final CourseTypeRepoJpa courseTypeRepoJpa;

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

    public void checkInstructorId(int instructorId){
        Instructor instructor=instructorRepoJpa.findById(instructorId).orElse(null);
        if(instructor==null){
            throw new BusinessExcepiton("BU ıd ye ait Eğitmen kaydı mevcut değildir");
        }
    }

    public void checkCourseTypeId(int courseTypeId){
        CourseType courseType=courseTypeRepoJpa.findById(courseTypeId).orElse(null);
        if (courseType==null){
            throw new BusinessExcepiton("Bu Id ye ait course tipi yok.Listeleme Başarısız");
        }
    }
}
