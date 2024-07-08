package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdInstructorResponse {
    private String name;
    private String lastName;
    private Date birthDate;
    private String department;
    private String email;
    private List<String>coursesGiven;
}
