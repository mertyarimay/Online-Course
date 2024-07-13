package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.usersCourses.UsersCoursesRepoJpa;

import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UsersCoursesRules {
    private final UsersCoursesRepoJpa usersCoursesRepoJpa;

    public void usersCheck(int usersId,int coursesId){
       if(usersCoursesRepoJpa.checkUsers(usersId,coursesId).isPresent()){
           throw new BusinessExcepiton("Aynı kullanıcı ile aynı kurs kaydı yaptığınız için kurs kaydı başarısız");
       }

    }
}
