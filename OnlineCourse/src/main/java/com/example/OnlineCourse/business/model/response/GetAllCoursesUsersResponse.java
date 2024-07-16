package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCoursesUsersResponse {
    private String name;
    private String lastName;
    private LocalDate coursesRegistrationDate;

}
