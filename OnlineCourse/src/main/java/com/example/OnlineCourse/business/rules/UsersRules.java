package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.users.UsersRepoJpa;
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
}
