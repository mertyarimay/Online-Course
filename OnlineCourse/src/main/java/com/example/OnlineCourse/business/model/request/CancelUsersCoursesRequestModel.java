package com.example.OnlineCourse.business.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelUsersCoursesRequestModel {
    private int usersId;
    private  int coursesId;
}
