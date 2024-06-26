package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCourseTypeRequestModel {
    @NotNull
    @Size(min = 1,max = 250)
    private String name;
    private int courseTitleId;
    private int id;
}
