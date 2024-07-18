package com.example.OnlineCourse.dao.instructor;


import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Instructor;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class InstructorRepoImpl implements InstructorRepo{
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;


    private static final String INSTRUCTOR_CREATE="INSERT INTO instructor " +
            "(name,last_name,email,department,birth_date,password) values(?,?,?,?,?,?)";

    private static final String INSTRUCTOR_GETALL = "SELECT i.name, i.last_name, i.birth_date, i.department, i.email, c.description " +
            "FROM instructor i " +
            "INNER JOIN courses c ON i.id = c.instructor_id";

    private static final String INSTRUCTOR_GETBYID="SELECT * FROM instructor where id=?";

    private static final String INSTRUCTOR_UPDATE="UPDATE instructor set department=?,email=?,password=? where id=?";
    private static  final String INSTRUCTOR_DELETE="DELETE FROM instructor where id=?";


    @Override
    public void create(Instructor instructor) {
        jdbcTemplate.update
                (INSTRUCTOR_CREATE,instructor.getName()
                        ,instructor.getLastName()
                        ,instructor.getEmail()
                        ,instructor.getDepartment()
                        ,instructor.getBirthDate()
                        ,passwordEncoder.encode(instructor.getPassword()));
    }



    public List<Instructor> getAll() {
        List<Instructor> instructors = jdbcTemplate.query(INSTRUCTOR_GETALL, (rs, rowNum) -> {
            // Her bir satır için yeni bir Instructor nesnesi oluşturulmalı
            Instructor instructor = new Instructor();
            instructor.setName(rs.getString("name"));
            instructor.setLastName(rs.getString("last_name"));
            instructor.setBirthDate(rs.getDate("birth_date"));
            instructor.setDepartment(rs.getString("department"));
            instructor.setEmail(rs.getString("email"));

            // Instructor nesnesine ait courses listesi oluşturulmalı
            List<Courses> courses = new ArrayList<>();

            // Her bir satır için yeni bir Course nesnesi oluşturulmalı ve listeye eklenmeli
            Courses course = new Courses();
            course.setDescription(rs.getString("description"));
            courses.add(course);

            // Instructor nesnesine courses listesi atanmalı
            instructor.setCourses(courses);

            return instructor;
        });

        return instructors;
    }


    @Override
    public Instructor getById(int id) {
        try {
            return jdbcTemplate.queryForObject(INSTRUCTOR_GETBYID,new Object[]{id},BeanPropertyRowMapper.newInstance(Instructor.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public Boolean update(Instructor instructor, int id) {
        try{
            int affectedRows=jdbcTemplate.update(INSTRUCTOR_UPDATE,instructor.getDepartment(),instructor.getEmail(),passwordEncoder.encode(instructor.getPassword()),id);
            return affectedRows>0;
        }catch(EmptyResultDataAccessException e){
            return false;
        }
    }


    @Override
    public Boolean delete(int id) {
        try{
            int affectedRows=jdbcTemplate.update(INSTRUCTOR_DELETE,id);
            return affectedRows>0;

        }catch (DataAccessException e){
            return false;
        }

    }
}
