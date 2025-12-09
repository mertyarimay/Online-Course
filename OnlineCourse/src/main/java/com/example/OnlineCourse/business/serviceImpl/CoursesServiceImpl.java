package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateCoursesRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateCoursesRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCoursesResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCoursesResponse;
import com.example.OnlineCourse.business.rules.CoursesRules;
import com.example.OnlineCourse.business.service.CoursesService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.config.util.JwtUtil;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Instructor;
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
    private final JwtUtil jwtUtil;
    private final InstructorRepoJpa instructorRepoJpa;

    @Override
    public CreateCoursesRequestModel create(CreateCoursesRequestModel createCoursesRequestModel,String token) {
        coursesRules.checkTypeId(createCoursesRequestModel.getCourseTypeId());
        Courses course=modelMapperService.forRequest().map(createCoursesRequestModel,Courses.class);
        //tokene bearer başlığını çıkartma
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String instructurId=jwtUtil.extractUserId(token);
        int insId=Integer.parseInt(instructurId);
        Instructor instructor=instructorRepoJpa.findById(insId).orElse(null);
        if(instructor!=null){
            course.setInstructor(instructor);
            coursesRepoJpa.save(course);
        }
        CreateCoursesRequestModel createCourseModel=modelMapperService.forRequest().map(course,CreateCoursesRequestModel.class);
        return createCourseModel;
    }

    @Override
    public List<GetAllCoursesResponse> getAll(Optional<Integer>instructorId,String token) {
        if(instructorId.isPresent()){
            if (token.startsWith("Bearer ")) {
                token = token.substring(7).trim();
            }
            String insId=jwtUtil.extractUserId(token);
            Integer intId=Integer.parseInt(insId);
            if(intId.equals(instructorId.get())){
                coursesRules.checkInstructorId(instructorId.get());
                List<Courses>courses=coursesRepoJpa.findByInstructorId(instructorId.get());
                List<GetAllCoursesResponse>getAllCoursesResponses=courses.stream()
                        .map(course -> modelMapperService.forResponse()
                                .map(course,GetAllCoursesResponse.class)).collect(Collectors.toList());
                return getAllCoursesResponses;

            }
        }
        return null;

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
    public UpdateCoursesRequestModel update(UpdateCoursesRequestModel updateCoursesRequestModel, int id,String token) {
        Courses course=coursesRepoJpa.findById(id).orElse(null);
        if(course!=null){
            if (token.startsWith("Bearer ")) {
                token = token.substring(7).trim();
            }
            String insId=jwtUtil.extractUserId(token);
            int instructorId=Integer.parseInt(insId);
            if(course.getInstructor().getId()==instructorId){
                course.setPrice(updateCoursesRequestModel.getPrice());
                coursesRules.checkPrice(course,id);
                coursesRepoJpa.save(course);
                UpdateCoursesRequestModel updateCourseRequestModel=modelMapperService.forRequest().map(course,UpdateCoursesRequestModel.class);
                return updateCourseRequestModel;
            }
            }
            return null;
    }

    @Override
    public Boolean delete(int id) {
        Courses course=coursesRepoJpa.findById(id).orElse(null);
        if(course!=null){
            coursesRepoJpa.deleteById(id);
            if(!coursesRepoJpa.existsById(id)){
                return true;
            }
        }
        return false;

    }
}
