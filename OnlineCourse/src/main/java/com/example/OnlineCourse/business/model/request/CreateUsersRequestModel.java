package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateUsersRequestModel {
    private int id;
    @Size(min = 1,max = 100)
    @NotNull
    private String name;
    @NotNull
    @Size(min = 1,max = 100)
    private  String lastName;
    @NotNull
    @Size(min = 11,max = 11)
    private String tckmlkNo;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Past(message = "Doğum Tarihi İleri Bir Tarih Olamaz")
    private LocalDate birthDate;

    private  int coursesId;
}
