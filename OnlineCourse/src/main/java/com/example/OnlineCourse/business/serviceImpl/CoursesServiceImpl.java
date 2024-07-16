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
        Courses courses=modelMapperService.forRequest().map(createCoursesRequestModel,Courses.class);
        coursesRepoJpa.save(courses);
        CreateCoursesRequestModel createCoursesRequestModel1=modelMapperService.forRequest().map(courses,CreateCoursesRequestModel.class);
        return createCoursesRequestModel1;
    }

    @Override
    public List<GetAllCoursesResponse> getAll(Optional<Integer> instructorId) {
        if(instructorId.isPresent()){
            coursesRules.checkInstructorId(instructorId.get());
            List<Courses>courses=coursesRepoJpa.findByInstructorId(instructorId.get());
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                    .map(courses1 -> modelMapperService.forResponse()
                            .map(courses1,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;
        }
        else {
            List<Courses>courses=coursesRepoJpa.findAll();
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                    .map(courses1 -> modelMapperService.forResponse()
                            .map(courses1,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;
        }
    }

    @Override
    public List<GetAllCoursesResponse> getAllCourseTypeId(Optional<Integer> courseTypeId) {
        if (courseTypeId.isPresent()){
            coursesRules.checkCourseTypeId(courseTypeId.get());
            List<Courses>courses=coursesRepoJpa.findByCourseTypeId(courseTypeId.get());
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream().map(courses1 -> modelMapperService.forResponse()
                    .map(courses1,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;
        }else{
            List<Courses>courses=coursesRepoJpa.findAll();
            List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                    .map(courses1 -> modelMapperService.forResponse()
                            .map(courses1,GetAllCoursesResponse.class)).collect(Collectors.toList());
            return getAllCoursesResponses;

        }
    }





    @Override
    public GetByIdCoursesResponse getById(int id) {
        Courses courses=coursesRepoJpa.findById(id).orElse(null);
        if(courses!=null){
            GetByIdCoursesResponse getByIdCoursesResponse=modelMapperService.forResponse().map(courses,GetByIdCoursesResponse.class);
            return getByIdCoursesResponse;
        }else {
            return null;
        }


    }

    @Override
    public UpdateCoursesRequestModel update(UpdateCoursesRequestModel updateCoursesRequestModel, int id) {
        Courses courses=coursesRepoJpa.findById(id).orElse(null);
        if(courses!=null){
            courses.setPrice(updateCoursesRequestModel.getPrice());
            coursesRules.checkPrice(courses,id);
            coursesRepoJpa.save(courses);
            UpdateCoursesRequestModel updateCoursesRequestModel1=modelMapperService.forRequest().map(courses,UpdateCoursesRequestModel.class);
            return updateCoursesRequestModel1;
        }else  {
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {
        Courses courses=coursesRepoJpa.findById(id).orElse(null);
        if(courses!=null){
            coursesRepoJpa.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
