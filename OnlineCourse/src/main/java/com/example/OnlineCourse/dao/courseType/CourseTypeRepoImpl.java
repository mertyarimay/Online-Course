package com.example.OnlineCourse.dao.courseType;

import com.example.OnlineCourse.entity.CourseTitle;
import com.example.OnlineCourse.entity.CourseType;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CourseTypeRepoImpl implements CourseTypeRepo {

    private final JdbcTemplate jdbcTemplate;

    private static  final String COURSE_TYPE_GETBYID="SELECT ct.id, ct.name, ct.course_title_id, ct2.title " +
            "FROM course_type ct " +
            "INNER JOIN course_title ct2 ON ct.course_title_id = ct2.id " +
            "WHERE ct.id = ?";


    private static final String COURSE_TYPE_DELETE ="DELETE from course_type where id=?";

    @Override
    public CourseType getById(int id) {
        try {
            return jdbcTemplate.queryForObject(COURSE_TYPE_GETBYID, new Object[]{id}, (rs, rowNum) -> {
                CourseType courseType = new CourseType();
                courseType.setId(rs.getInt("id"));
                courseType.setName(rs.getString("name"));

                CourseTitle courseTitle = new CourseTitle();
                courseTitle.setId(rs.getInt("course_title_id"));
                courseTitle.setTitle(rs.getString("title"));

                courseType.setCourseTitle(courseTitle);
                return courseType;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    @Override
    public Boolean delete(int id) {
      try{
        int affectedRows=jdbcTemplate.update(COURSE_TYPE_DELETE,id);
        return affectedRows>0; //affected rows etkilenen veri tabanı satır sayısını temsil eder update ve delete de kullanılır
        }catch(DataAccessException e){
          return false;
      }

    }
}
