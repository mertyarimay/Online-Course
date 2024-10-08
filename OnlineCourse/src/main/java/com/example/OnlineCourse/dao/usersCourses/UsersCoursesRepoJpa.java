package com.example.OnlineCourse.dao.usersCourses;

import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.UsersCourses;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersCoursesRepoJpa extends JpaRepository<UsersCourses,Integer> {
    List<UsersCourses>findByUsersId(Integer usersId);
    List<UsersCourses>findByCoursesId(Integer coursesId);

    @Transactional  //modifyingle genelde beraber kullanılır veri bütünlüğünü korumak için kullanılır jpa da
    @Modifying
    @Query("DELETE FROM UsersCourses uc WHERE uc.users.id = :usersId AND uc.courses.id = :coursesId")
    int cancelByCoursesIdAndUsersId(Integer usersId, Integer coursesId);

    @Query("SELECT uc FROM UsersCourses uc WHERE uc.users.id = :usersId AND uc.courses.id = :coursesId")
    Optional<UsersCourses> checkUsers(@Param("usersId") int usersId, @Param("coursesId") int coursesId);
}


