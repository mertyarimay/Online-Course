package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUsersRequestModel {
    private String email;

    @Size(min =11,max = 11)
    private String password;

    @Size(min =11,max = 11)
    private String oldPassword;
}
