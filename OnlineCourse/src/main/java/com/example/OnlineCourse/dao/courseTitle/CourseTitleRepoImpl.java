package com.example.OnlineCourse.dao.courseTitle;



import com.example.OnlineCourse.entity.CourseTitle;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CourseTitleRepoImpl implements CourseTitleRepo {

    private final JdbcTemplate jdbcTemplate;
    private static final String COURSE_TITLE_GETALL = "SELECT * FROM course_title";
    private static final String COURSE_TITLE_GETBYID = "SELECT*FROM course_title where id=?";
    private static  final String COURSE_TITLE_CREATE="INSERT INTO course_title (title) values(?)";


    @Override
    public void create(CourseTitle courseTitle) {
        jdbcTemplate.update(COURSE_TITLE_CREATE,courseTitle.getTitle());

    }


    @Override
    public List<CourseTitle> getAll() {
        RowMapper<CourseTitle> rowMapper = (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            return new CourseTitle(id, title);

        };
        return jdbcTemplate.query(COURSE_TITLE_GETALL, rowMapper);
    }



    @Override
    public CourseTitle getById(int id) {
        CourseTitle courseTitle=null;
        try {
            return jdbcTemplate.queryForObject(COURSE_TITLE_GETBYID, new Object[]{id}, (resultSet, rowNum) -> {
                int courseId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                return new CourseTitle(courseId, title);
            });

        }catch (EmptyResultDataAccessException e){

        }
        return courseTitle;


    }

}


