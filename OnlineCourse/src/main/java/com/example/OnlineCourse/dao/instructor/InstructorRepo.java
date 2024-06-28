package com.example.OnlineCourse.dao.instructor;

import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.entity.Instructor;

import java.util.List;

public interface InstructorRepo {
    void create(Instructor instructor);
    List<Instructor> getAll();
    Instructor getById(int id);
    Boolean update(Instructor instructor,int id);
    Boolean delete(int id);
}
