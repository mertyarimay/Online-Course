package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstructorRules {
    private final InstructorRepoJpa instructorRepoJpa;
    public void checkMail(String email){
        if(instructorRepoJpa.existsByEmail(email)){
            throw new BusinessExcepiton("Bu Mail Bir Eğitmene Aittir Doğrulama Kodu İçin Farklı Bir Mail Girmeniz Gerekmektedir.");
        }


    }
}
