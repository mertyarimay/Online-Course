package com.example.OnlineCourse.business.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetByIdUsersResponse {
    private String name;
    private  String lastName;
    private String email;
    private String coursesDescription;

}
