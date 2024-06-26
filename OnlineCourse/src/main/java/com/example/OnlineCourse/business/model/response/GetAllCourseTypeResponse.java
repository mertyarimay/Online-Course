package com.example.OnlineCourse.business.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCourseTypeResponse {
    private int id;
    private String name;
    private String Title;
}
