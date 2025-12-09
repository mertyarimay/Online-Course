package com.example.OnlineCourse.business.model.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class TokenModel {
    private String userId;
    private String userName;
    private String roleName;

}
