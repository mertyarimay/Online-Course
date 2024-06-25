package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetByIdCourseTitleResponse {
    private int id;
    private String title;
}
