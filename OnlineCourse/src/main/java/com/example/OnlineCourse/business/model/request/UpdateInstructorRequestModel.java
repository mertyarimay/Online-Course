package com.example.OnlineCourse.business.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInstructorRequestModel {
    private String department;
    private String email;
}
