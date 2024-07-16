package com.example.OnlineCourse.business.model.response;


import com.example.OnlineCourse.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUsersCoursesResponse {
    private String coursesDescription;
    private LocalDate coursesRegistrationDate;
    private double coursePrice;

}
