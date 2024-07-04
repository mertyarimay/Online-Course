package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.rules.InstructorRules;
import com.example.OnlineCourse.business.service.InstructorService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.instructor.InstructorRepo;
import com.example.OnlineCourse.entity.Instructor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final ModelMapperService modelMapperService;
    private final InstructorRepo instructorRepo;
    private final InstructorRules instructorRules;


    @Override
    public CreateInstructorRequestModel create(CreateInstructorRequestModel createInstructorRequestModel) {
        instructorRules.checkMail(createInstructorRequestModel.getEmail());
        Instructor instructor=modelMapperService.forRequest().map(createInstructorRequestModel,Instructor.class);
          instructorRepo.create(instructor);
          CreateInstructorRequestModel createInstructorRequestModel1=modelMapperService.forRequest().map(instructor,CreateInstructorRequestModel.class);
          return createInstructorRequestModel1;
    }



    @Override
    public List<GetAllInstructorResponse> getAll() {
        List<Instructor> instructor=instructorRepo.getAll();
        List<GetAllInstructorResponse>getAllInstructorResponses=instructor
                .stream().map(instructor1 -> modelMapperService.forResponse()
                        .map(instructor1, GetAllInstructorResponse.class)).collect(Collectors.toList());
        return getAllInstructorResponses;
    }


    @Override
    public GetByIdInstructorResponse getById(int id) {
        Instructor instructor=instructorRepo.getById(id);
        if(instructor!=null){
            GetByIdInstructorResponse getByIdInstructorResponse=modelMapperService.forResponse().map(instructor,GetByIdInstructorResponse.class);
            return getByIdInstructorResponse;
        }else {
            return null;
        }
    }


    @Override
    public Boolean update(UpdateInstructorRequestModel updateInstructorRequestModel, int id) {
     Instructor instructor=modelMapperService.forRequest().map(updateInstructorRequestModel,Instructor.class);
     Boolean update=instructorRepo.update(instructor,id);
     if(update==true){
         return true;
     }else {
         return false;
     }


    }

    @Override
    public Boolean delete(int id) {
        Boolean delete=instructorRepo.delete(id);
       return delete;
    }

}
