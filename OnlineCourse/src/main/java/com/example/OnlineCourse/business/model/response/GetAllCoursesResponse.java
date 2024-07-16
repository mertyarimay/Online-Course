package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllCoursesResponse {
    private String courseTypeName;
    private String description;
    private String instructorName;
    private String instructorLastName;
    private double price;
    private LocalDate publishedAt;
    private LocalDate updateDate;


}
