package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllUsersResponse {
    private String name;
    private  String lastName;
    private String email;
    private String coursesDescription;


}
