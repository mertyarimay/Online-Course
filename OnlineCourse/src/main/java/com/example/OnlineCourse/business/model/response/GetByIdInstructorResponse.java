package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdInstructorResponse {
    private String name;
    private String lastName;
    private Date birthDate;
    private String department;
    private String email;
}
