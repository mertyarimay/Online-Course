package com.example.OnlineCourse.dao.users;

import com.example.OnlineCourse.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepoJpa extends JpaRepository<Users,Integer> {
    boolean existsByTckmlkNo(String tckmlkNo);
}
