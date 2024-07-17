package com.example.OnlineCourse.dao.users;

import com.example.OnlineCourse.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepoJpa extends JpaRepository<Users,Integer> {
    boolean existsByTckmlkNo(String tckmlkNo);
    boolean existsByEmail(String email);
    List<Users>findByCoursesId(Integer coursesId);
    Optional<Users> findByEmail(String email);
}
