package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdCoursesResponse {
    private String courseTypeName;
    private String description;
    private String instructionName;
    private double price;
    private LocalDate publishedAt;
}
