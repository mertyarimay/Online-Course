package com.example.OnlineCourse.dao.instructor;


import com.example.OnlineCourse.entity.Instructor;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class InstructorRepoImpl implements InstructorRepo{
    private final JdbcTemplate jdbcTemplate;


    private static final String INSTRUCTOR_CREATE="INSERT INTO instructor " +
            "(name,last_name,email,department,birth_date) values(?,?,?,?,?)";
    private static final String INSTRUCTOR_GETALL="SELECT*FROM instructor";
    private static final String INSTRUCTOR_GETBYID="SELECT * FROM instructor where id=?";

    private static final String INSTRUCTOR_UPDATE="UPDATE instructor set department=? where id=?";
    private static  final String INSTRUCTOR_DELETE="DELETE FROM instructor where id=?";


    @Override
    public void create(Instructor instructor) {
        jdbcTemplate.update
                (INSTRUCTOR_CREATE,instructor.getName()
                        ,instructor.getLastName()
                        ,instructor.getEmail()
                        ,instructor.getDepartment()
                        ,instructor.getBirthDate());
    }



    @Override
    public List<Instructor> getAll() {
        return jdbcTemplate.query(INSTRUCTOR_GETALL,BeanPropertyRowMapper.newInstance(Instructor.class));
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
            int affectedRows=jdbcTemplate.update(INSTRUCTOR_UPDATE,instructor.getDepartment(),id);
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
