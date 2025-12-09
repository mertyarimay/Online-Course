package com.example.OnlineCourse.dao.admin;


import com.example.OnlineCourse.entity.Admin;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminRepoImpl implements AdminRepo{
    public final JdbcTemplate jdbcTemplate;
    public final PasswordEncoder passwordEncoder;

    private static final String CREATE_ADMIN="INSERT INTO admin (user_name,password,role_id) VALUES(?,?,?)";

    @Override
    public void create(Admin admin) {
      int affectedRow= jdbcTemplate.update(CREATE_ADMIN,admin.getUserName(),passwordEncoder.encode(admin.getPassword()),admin.getRole().getId());
       if(affectedRow<0){
         throw new BusinessExcepiton("Kayıt İşlemi başarısız");
       }
    }

}
