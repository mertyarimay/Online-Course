package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTypeResponse;
import com.example.OnlineCourse.business.rules.CourseTypeRules;
import com.example.OnlineCourse.business.service.CourseTypeService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.courseType.CourseTypeRepoJpa;
import com.example.OnlineCourse.entity.CourseType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class courseTypeServiceImpl implements CourseTypeService {
    private final CourseTypeRepoJpa courseTypeRepoJpa;
    private final CourseTypeRules courseTypeRules;
    private final ModelMapperService modelMapperService;
    @Override
    public CreateCourseTypeRequestModel create(CreateCourseTypeRequestModel createCourseTypeRequestModel) {
         courseTypeRules.checkName(createCourseTypeRequestModel.getName());
        CourseType courseType=modelMapperService.forRequest().map(createCourseTypeRequestModel,CourseType.class);
        courseTypeRepoJpa.save(courseType);
        CreateCourseTypeRequestModel createCourseTypeRequestModel1=modelMapperService.forRequest().map(courseType,CreateCourseTypeRequestModel.class);
        return createCourseTypeRequestModel1;
    }

    @Override
    public List<GetAllCourseTypeResponse> getAll(Optional<Integer> courseTitleId) {
        if(courseTitleId.isPresent()){
            List<CourseType>courseTypes=courseTypeRepoJpa.findByCourseTitleId(courseTitleId.get());
            List<GetAllCourseTypeResponse>getAllCourseTypeResponses=courseTypes.stream()
                    .map(courseType -> modelMapperService.forResponse()
                            .map(courseType, GetAllCourseTypeResponse.class)).collect(Collectors.toList());
            return getAllCourseTypeResponses;
        }
        else {
            List<CourseType>courseTypes=courseTypeRepoJpa.findAll();
            List<GetAllCourseTypeResponse>getAllCourseTypeResponses=courseTypes.stream()
                    .map(courseType -> modelMapperService.forResponse()
                            .map(courseType, GetAllCourseTypeResponse.class)).collect(Collectors.toList());
            return getAllCourseTypeResponses;
        }
    }
}
