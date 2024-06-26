package com.example.OnlineCourse.dao.courseTitle;



import com.example.OnlineCourse.entity.CourseTitle;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        return jdbcTemplate.query(COURSE_TITLE_GETALL, BeanPropertyRowMapper.newInstance(CourseTitle.class));
    }



    @Override
    public CourseTitle getById(int id) {
        try {
            return jdbcTemplate.queryForObject(COURSE_TITLE_GETBYID, new Object[]{id}, BeanPropertyRowMapper.newInstance(CourseTitle.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    }




