package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstructorRequestModel {
    private int id;
    @NotNull
    @Size(min = 1,max = 50)
    private String name;
    @NotNull
    @Size(min = 1,max = 50)
    private String lastName;
    @NotNull
    @Past(message = "Doğum Tarihi Gelecek Bir Tarih Olamaz.")
    private Date birthDate;
    @NotNull
    private String department;
    @NotNull
    @Email
    private String email;

    @Size(min = 11,max = 11)
    private String password;

    private int usersId;
}
