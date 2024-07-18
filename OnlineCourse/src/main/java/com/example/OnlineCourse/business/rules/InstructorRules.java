package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.entity.Instructor;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstructorRules {
    private final InstructorRepoJpa instructorRepoJpa;
    private final PasswordEncoder passwordEncoder;
    public void checkMail(String email){
        if(instructorRepoJpa.existsByEmail(email)){
            throw new BusinessExcepiton("Bu Mail Bir Eğitmene Aittir Doğrulama Kodu İçin Farklı Bir Mail Girmeniz Gerekmektedir.");
        }
    }
    public Boolean checkInstructor(int id,String oldPassword){
        Instructor instructor=instructorRepoJpa.findById(id).orElse(null);
        if(instructor!=null&&passwordEncoder.matches(oldPassword,instructor.getPassword())){
            return true;

        }else{
            return false;
        }
    }

    public void checkOldPassword(int id,String oldPassword){
        Boolean instructor=checkInstructor(id,oldPassword);
        if(instructor==false){
            throw new BusinessExcepiton("Eski şifreniz yanlış Şifre Güncelleme işlemi BAŞARISIZ..");
        }
    }
}
