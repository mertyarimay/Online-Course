package com.example.OnlineCourse.business.rules;

import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoursesRules {
    private final CoursesRepoJpa coursesRepoJpa;
}
