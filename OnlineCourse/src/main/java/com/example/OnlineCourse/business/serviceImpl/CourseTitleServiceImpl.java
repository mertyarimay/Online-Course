package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.UpdateCourseTitleRequestModel;
import com.example.OnlineCourse.business.service.CourseTitleService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.courseTitle.CourseTitleRepo;
import com.example.OnlineCourse.dao.courseTitle.CourseTitleRepoJpa;
import com.example.OnlineCourse.entity.CourseTitle;
import com.example.OnlineCourse.business.model.request.CreateCourseTitleRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllCourseTitleResponse;
import com.example.OnlineCourse.business.model.response.GetByIdCourseTitleResponse;
import com.example.OnlineCourse.business.rules.CourseTitleRules;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseTitleServiceImpl implements CourseTitleService {
    private final CourseTitleRepoJpa courseTitleRepoJpa;
    private final CourseTitleRepo courseTitleRepo;
    private final ModelMapperService modelMapperService;
    private final CourseTitleRules courseTitleRules;


    @Override
    public CreateCourseTitleRequestModel create(CreateCourseTitleRequestModel createCourseTitleRequestModel) {
        courseTitleRules.checkTitle(createCourseTitleRequestModel.getTitle());
        CourseTitle courseTitle=modelMapperService.forRequest()
                .map(createCourseTitleRequestModel,CourseTitle.class);
        courseTitleRepo.create(courseTitle);
        CreateCourseTitleRequestModel createCourseTitleModel=modelMapperService.forRequest()
                .map(courseTitle,CreateCourseTitleRequestModel.class);
        return createCourseTitleModel;

    }



    @Override
    public List<GetAllCourseTitleResponse> getAll() {
      List<CourseTitle>courseTitles=courseTitleRepo.getAll();
      List<GetAllCourseTitleResponse>getAllCourseTitleResponses=courseTitles.stream()
              .map(courseTitle -> modelMapperService.forResponse()
                      .map(courseTitle, GetAllCourseTitleResponse.class)).collect(Collectors.toList());
      return getAllCourseTitleResponses;
    }

    @Override
    public GetByIdCourseTitleResponse getById(int id) {
        CourseTitle courseTitle = courseTitleRepo.getById(id);
        if(courseTitle!=null){
            GetByIdCourseTitleResponse getByIdCourseTitleResponse=modelMapperService.forResponse()
                    .map(courseTitle,GetByIdCourseTitleResponse.class);
            return getByIdCourseTitleResponse;
        }else {
            return null;
        }

    }


    @Override
    public UpdateCourseTitleRequestModel update(UpdateCourseTitleRequestModel updateCourseTitleRequestModel, int id) {
     CourseTitle courseTitle=courseTitleRepoJpa.findById(id).orElse(null);
     if (courseTitle==null){
         return null;
     }else {
         courseTitle.setTitle(updateCourseTitleRequestModel.getTitle());
         courseTitleRules.checkTitle(courseTitle.getTitle());
         courseTitleRepoJpa.save(courseTitle);
     UpdateCourseTitleRequestModel updateCourseTitleModel=modelMapperService.forRequest()
             .map(courseTitle,UpdateCourseTitleRequestModel.class);
     return updateCourseTitleModel;
     }
    }

    @Override
    public Boolean delete(int id) {
        Optional<CourseTitle>courseTitle=courseTitleRepoJpa.findById(id);
        if(courseTitle.isPresent()){
            courseTitleRepoJpa.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }
}
