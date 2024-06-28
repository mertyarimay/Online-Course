package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllInstructorResponse {
    private String name;
    private String lastName;
    private Date birthDate;
    private String department;
    private String email;
}
