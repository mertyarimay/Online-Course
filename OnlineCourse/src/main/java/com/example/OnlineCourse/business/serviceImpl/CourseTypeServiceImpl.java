package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateCourseTypeRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTypeResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCourseTypeResponse;
import com.example.OnlineCourse.business.rules.CourseTitleRules;
import com.example.OnlineCourse.business.rules.CourseTypeRules;
import com.example.OnlineCourse.business.service.CourseTypeService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.courseType.CourseTypeRepoJpa;
import com.example.OnlineCourse.entity.CourseType;
import com.example.OnlineCourse.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseTypeServiceImpl implements CourseTypeService {
    private final CourseTypeRepoJpa courseTypeRepoJpa;
    private final CourseTypeRules courseTypeRules;
    private final ModelMapperService modelMapperService;
    private final CourseTitleRules courseTitleRules;


    @Override
    @Transactional
    public CreateCourseTypeRequestModel create(CreateCourseTypeRequestModel createCourseTypeRequestModel) {
        courseTypeRules.checkName(createCourseTypeRequestModel.getName());
        courseTypeRules.checkCourseTitleId(createCourseTypeRequestModel.getCourseTitleId());
        CourseType courseType = modelMapperService.forRequest().map(createCourseTypeRequestModel, CourseType.class);
        courseTypeRepoJpa.save(courseType);
        CreateCourseTypeRequestModel createCourseTypeModel = modelMapperService.forRequest().map(courseType, CreateCourseTypeRequestModel.class);
        return createCourseTypeModel;
    }


    @Override
    public List<GetAllCourseTypeResponse> getAll(Optional<Integer> courseTitleId) {
        if (courseTitleId.isPresent()) {
            courseTitleRules.checkTitleId(courseTitleId.get());
            List<CourseType> courseTypes = courseTypeRepoJpa.findByCourseTitleId(courseTitleId.get());
            List<GetAllCourseTypeResponse> getAllCourseTypeResponses = courseTypes.stream()
                    .map(courseType -> modelMapperService.forResponse()
                            .map(courseType, GetAllCourseTypeResponse.class)).collect(Collectors.toList());
            return getAllCourseTypeResponses;
        } else {
            List<CourseType> courseTypes = courseTypeRepoJpa.findAll();
            List<GetAllCourseTypeResponse> getAllCourseTypeResponses = courseTypes.stream()
                    .map(courseType -> modelMapperService.forResponse()
                            .map(courseType, GetAllCourseTypeResponse.class)).collect(Collectors.toList());
            return getAllCourseTypeResponses;
        }
    }


    @Override
    public GetByIdCourseTypeResponse getById(int id) {
        CourseType courseType = courseTypeRepoJpa.findById(id).orElse(null);
        if (courseType == null) {
            throw new NotFoundException("Bu id ye ait kayıt yoktur");
        }
        GetByIdCourseTypeResponse getByIdCourseTypeResponse = modelMapperService.forResponse()
                .map(courseType, GetByIdCourseTypeResponse.class);
        return getByIdCourseTypeResponse;
    }


    @Override
    @Transactional
    public UpdateCourseTypeRequestModel update(UpdateCourseTypeRequestModel updateCourseTypeRequestModel, int id) {
        CourseType courseType = courseTypeRepoJpa.findById(id).orElse(null);
        if (courseType != null) {
            courseTypeRules.checkName(updateCourseTypeRequestModel.getName());
            courseType.setName(updateCourseTypeRequestModel.getName());
            courseTypeRepoJpa.save(courseType);
            UpdateCourseTypeRequestModel updateCourseTypeModel = modelMapperService.forRequest().map(courseType, UpdateCourseTypeRequestModel.class);
            return updateCourseTypeModel;
        } else {
            throw new NotFoundException("Bu Id ye Ait Kayıt Bulunamamıştır Güncelleme İşlemi Başarısız...");
        }
    }


    @Override
    @Transactional
    public Boolean delete(int id) {
        if (courseTypeRepoJpa.existsById(id)) {
            courseTypeRepoJpa.deleteById(id);
            return !courseTypeRepoJpa.existsById(id);
        }
        return false;
    }
}

