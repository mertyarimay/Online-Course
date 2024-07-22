package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.entity.Users;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersRules {
    private final UsersRepoJpa usersRepoJpa;
    private final PasswordEncoder passwordEncoder;
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
            throw new BusinessExcepiton(("Girdiğiniz mail Bir kullanıcıya Kayıtlı Başka Bir Mail Kullanmanız Gerekmektedir."));
        }

    }
    public Boolean checkPassword(int id ,String oldPassword){
        Users users=usersRepoJpa.findById(id).orElse(null);
        if(users!=null&&passwordEncoder.matches(oldPassword, users.getPassword())){
            return true;

        }else {
            return false;
        }

    }
    public void checkOldPassword(int id,String oldPassword){
        Boolean check=checkPassword(id,oldPassword);
        if (check==false){
            throw new BusinessExcepiton("Eski şifreniz Doğru DEĞİLDİR Şifre Güncelleme İşleminiz Başarısız.");
        }
    }

}
