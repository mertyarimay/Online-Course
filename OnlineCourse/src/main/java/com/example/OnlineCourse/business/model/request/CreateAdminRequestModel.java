package com.example.OnlineCourse.business.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminRequestModel {
    @NotNull
    @Size(min = 1)
    private String kullaniciAdi;
    @NotNull
    @Size(min = 11,max = 11)
    private String sifre;
}
