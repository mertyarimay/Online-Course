package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCoursesRequestModel {
    @NotNull
    private int courseTypeId;
    @NotNull
    private String description;
    @NotNull
    private double price;
    @NotNull
    private int instructorId;

    private int id;
}
