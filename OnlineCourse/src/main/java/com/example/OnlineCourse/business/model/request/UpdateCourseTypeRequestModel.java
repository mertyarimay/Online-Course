package com.example.OnlineCourse.business.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseTypeRequestModel {
    private int id;
    private String name;
}
