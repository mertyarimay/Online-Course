package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateCourseTitleRequestModel {
    @NotNull
    @Size(min = 1,max = 250)
    private String title;
}
