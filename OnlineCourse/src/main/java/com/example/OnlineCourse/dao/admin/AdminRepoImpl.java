package com.example.OnlineCourse.dao.admin;


import com.example.OnlineCourse.entity.Admin;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AdminRepoImpl implements AdminRepo{
    public final JdbcTemplate jdbcTemplate;
    public final PasswordEncoder passwordEncoder;

    private static final String CREATE_ADMIN="INSERT INTO admin (kullanici_adi,sifre) VALUES(?,?)";

    @Override
    public Admin create(Admin admin) {
        jdbcTemplate.update(CREATE_ADMIN,admin.getKullaniciAdi(),passwordEncoder.encode(admin.getSifre()));
        return admin;
    }

}
