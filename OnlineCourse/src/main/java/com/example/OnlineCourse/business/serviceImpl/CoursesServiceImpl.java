package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCoursesResponse;
import com.example.OnlineCourse.business.rules.CoursesRules;
import com.example.OnlineCourse.business.service.CoursesService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoursesServiceImpl implements CoursesService {
    private final CoursesRepoJpa coursesRepoJpa;
    private final ModelMapperService modelMapperService;
    private final CoursesRules coursesRules;

    @Override
    public CreateCoursesRequestModel create(CreateCoursesRequestModel createCoursesRequestModel) {
        coursesRules.checkTypeId(createCoursesRequestModel.getCourseTypeId());
        coursesRules.instructorId(createCoursesRequestModel.getInstructorId());
        Courses course=modelMapperService.forRequest().map(createCoursesRequestModel,Courses.class);
        coursesRepoJpa.save(course);
        CreateCoursesRequestModel createCourseModel=modelMapperService.forRequest().map(course,CreateCoursesRequestModel.class);
        return createCourseModel;
    }

    @Override
    public List<GetAllCoursesResponse> getAll(Optional<Integer> instructorId) {
        if(instructorId.isPresent()){
            coursesRules.checkInstructorId(instructorId.get());
            List<Courses>courses=coursesRepoJpa.findByInstructorId(instructorId.get());
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                    .map(course -> modelMapperService.forResponse()
                            .map(course,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;
        }
        else {
            List<Courses>courses=coursesRepoJpa.findAll();
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                    .map(course -> modelMapperService.forResponse()
                            .map(course,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;
        }
    }

    @Override
    public List<GetAllCoursesResponse> getAllCourseTypeId(Optional<Integer> courseTypeId) {
        if (courseTypeId.isPresent()){
            coursesRules.checkCourseTypeId(courseTypeId.get());
            List<Courses>courses=coursesRepoJpa.findByCourseTypeId(courseTypeId.get());
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream().map(course -> modelMapperService.forResponse()
                    .map(course,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;
        }else{
            List<Courses>courses=coursesRepoJpa.findAll();
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                    .map(course -> modelMapperService.forResponse()
                            .map(course,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;

        }
    }





    @Override
    public GetByIdCoursesResponse getById(int id) {
        Courses course=coursesRepoJpa.findById(id).orElse(null);
        if(course!=null){
            GetByIdCoursesResponse getByIdCoursesResponse=modelMapperService.forResponse().map(course,GetByIdCoursesResponse.class);
            return getByIdCoursesResponse;
        }else {
            return null;
        }


    }

    @Override
    public UpdateCoursesRequestModel update(UpdateCoursesRequestModel updateCoursesRequestModel, int id) {
        Courses course=coursesRepoJpa.findById(id).orElse(null);
        if(course!=null){
            course.setPrice(updateCoursesRequestModel.getPrice());
            coursesRules.checkPrice(course,id);
            coursesRepoJpa.save(course);
            UpdateCoursesRequestModel updateCourseRequestModel=modelMapperService.forRequest().map(course,UpdateCoursesRequestModel.class);
            return updateCourseRequestModel;
        }else  {
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {
        Courses course=coursesRepoJpa.findById(id).orElse(null);
        if(course!=null){
            coursesRepoJpa.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
