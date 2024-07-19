package com.example.OnlineCourse.dao.admin;

import com.example.OnlineCourse.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepoJpa extends JpaRepository<Admin,Integer> {
  Optional<Admin>findByKullaniciAdi(String kullaniciAdi);
}
