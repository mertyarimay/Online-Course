package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateInstructorLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.rules.InstructorRules;
import com.example.OnlineCourse.business.service.InstructorService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.instructor.InstructorRepo;
import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Instructor;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final ModelMapperService modelMapperService;
    private final InstructorRepo instructorRepo;
    private final InstructorRules instructorRules;
    private final InstructorRepoJpa instructorRepoJpa;
    private final PasswordEncoder passwordEncoder;


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
        List<Instructor> instructors=instructorRepoJpa.findAll();
        List<GetAllInstructorResponse> getAllInstructorResponses = instructors.stream()
                //her bir instructor için çevirme işlemi yapılıyor gibi
                .map(instructor -> {
                    GetAllInstructorResponse response = new GetAllInstructorResponse();
                    response.setName(instructor.getName());
                    response.setLastName(instructor.getLastName());
                    response.setBirthDate(instructor.getBirthDate());
                    response.setDepartment(instructor.getDepartment());
                    response.setEmail(instructor.getEmail());

                    List<String> descriptions = instructor.getCourses().stream()
                            .map(Courses::getDescription)
                            .collect(Collectors.toList());


                    response.setCoursesGiven(descriptions);

                    return response;
                })
                .collect(Collectors.toList());
        return getAllInstructorResponses;

    }





    @Override
    public GetByIdInstructorResponse getById(int id) {
        Instructor instructor=instructorRepoJpa.findById(id).orElse(null);
        if(instructor!=null){
            GetByIdInstructorResponse getByIdInstructorResponse=new GetByIdInstructorResponse();
            getByIdInstructorResponse.setName(instructor.getName());
            getByIdInstructorResponse.setLastName(instructor.getLastName());
            getByIdInstructorResponse.setEmail(instructor.getEmail());
            getByIdInstructorResponse.setDepartment(instructor.getDepartment());
            getByIdInstructorResponse.setBirthDate(instructor.getBirthDate());


            List<String>courseGivens=instructor.getCourses().stream().map(Courses::getDescription).collect(Collectors.toList());
            getByIdInstructorResponse.setCoursesGiven(courseGivens);
            return getByIdInstructorResponse;
        }else {
            return null;
        }
    }


    @Override
    public Boolean update(UpdateInstructorRequestModel updateInstructorRequestModel, int id) {
     Instructor instructor=modelMapperService.forRequest().map(updateInstructorRequestModel,Instructor.class);
     instructorRules.checkOldPassword(id,updateInstructorRequestModel.getOldPassword());
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

    @Override
    public Boolean instructorLogin(CreateInstructorLoginRequestModel createInstructorLoginRequestModel) {
      Instructor instructor=instructorRepoJpa.findByEmail(createInstructorLoginRequestModel.getEmail()).orElse(null);
      if (instructor!=null&&passwordEncoder.matches(createInstructorLoginRequestModel.getPassword(),instructor.getPassword())){
            return true;
        }
      else{
          return false;

      }

    }

}
