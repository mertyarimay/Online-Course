package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCourseTitleResponse {
    private int id;
    private String title;
}
