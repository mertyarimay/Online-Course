package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.entity.Users;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersRules {
    private final UsersRepoJpa usersRepoJpa;
    public void existByTckmlkNo(String tckmlkNo){
        if (usersRepoJpa.existsByTckmlkNo(tckmlkNo)){
            throw new BusinessExcepiton("Aynı TC kimlik No ile Kayıt mevcuttur");
        }
    }
    public void usersIdCheck(int usersId){
       Users users= usersRepoJpa.findById(usersId).orElse(null);
       if(users==null){
           throw new BusinessExcepiton("Bu ıd ye ait bir kullanıcı yoktur");
       }
    }
    public void existByEmail(String email){
        if(usersRepoJpa.existsByEmail(email)){
            throw new BusinessExcepiton(("Güncelleme yaparken bir önceki malinizi girdiniz Güncelleme işlemi başarısız."));
        }
    }
}
