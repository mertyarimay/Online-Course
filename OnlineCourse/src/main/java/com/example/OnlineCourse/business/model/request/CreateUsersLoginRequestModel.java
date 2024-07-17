package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateUsersLoginRequestModel {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 11,max = 11)
    private String password;
}
