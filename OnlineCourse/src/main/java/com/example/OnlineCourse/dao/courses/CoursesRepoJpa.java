package com.example.OnlineCourse.dao.courses;

import com.example.OnlineCourse.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CoursesRepoJpa extends JpaRepository<Courses,Integer> {
  List<Courses>findByInstructorId(Integer instructorId);
  List<Courses>findByCourseTypeId(Integer courseTypeId);

  @Query("SELECT c FROM Courses c WHERE c.id = :id AND c.price = :price")
  Optional<Courses> checkPrice(@Param("id") int id, @Param("price") double price);

}
