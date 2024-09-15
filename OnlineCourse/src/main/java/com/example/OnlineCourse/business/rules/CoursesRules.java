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


    //Courses ta güncelleme yaparken fiyat güncellemesi var önceki fiyatı girememe durumu
    public Courses checkPrice(Courses course,int id) {
        if (coursesRepoJpa.checkPrice(id,course.getPrice()).isPresent()){
           throw new BusinessExcepiton("Fiyat bir önceki fiyatınız ile aynı güncelleme yapamazssınız.");
        }else{
            return course;
        }

    }

    //usersların course kaydı için  yazılan kural userscoursesta kullanılıyor
    public void checkCoursesId(Integer coursesId){
        Courses course=coursesRepoJpa.findById(coursesId).orElse(null);
        if(course==null){
            throw new BusinessExcepiton("Bu ID ye Ait course Kaydı Mevcut değildir");
        }
    }
   //instructor ıdye göre listeleme
    public void checkInstructorId(int instructorId){
        Instructor instructor=instructorRepoJpa.findById(instructorId).orElse(null);
        if(instructor==null){
            throw new BusinessExcepiton("BU ıd ye ait Eğitmen kaydı mevcut değildir");
        }
    }
  //courseType Id ye göre listeleme kuralı
    public void checkCourseTypeId(int courseTypeId){
        CourseType courseType=courseTypeRepoJpa.findById(courseTypeId).orElse(null);
        if (courseType==null){
            throw new BusinessExcepiton("Bu Id ye ait course tipi yok.Listeleme Başarısız");
        }
    }
    //kurs kayıt işlemi için yazılan kural

    public void checkTypeId(Integer courseTypeId){
        CourseType courseType=courseTypeRepoJpa.findById(courseTypeId).orElse(null);
        if(courseType==null){
            throw new BusinessExcepiton("Course Kayıt Ederken Var olmayan bir courseTypeId girdiniz Kurs Kaydı BAŞARISIZ.");
        }

    }
    //kurs kayıt işlemi için yazılan kural
    public void instructorId(Integer instructorId){
        Instructor instructor=instructorRepoJpa.findById(instructorId).orElse(null);
        if(instructor==null){
            throw new BusinessExcepiton("Course Kayıt Ederken Var olmayan bir instructorId girdiniz Kurs Kaydı BAŞARISIZ.");
        }

    }
}
