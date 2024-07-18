package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInstructorRequestModel {


    @Email
    private String email;
    @Size(min = 11,max = 11)
    private String password;

    @Size(min = 11,max = 11)
    private String oldPassword;
}
