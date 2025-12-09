package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateInstructorLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.rules.InstructorRules;
import com.example.OnlineCourse.business.service.InstructorService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.config.util.JwtUtil;
import com.example.OnlineCourse.dao.courses.CoursesRepoJpa;
import com.example.OnlineCourse.dao.instructor.InstructorRepo;
import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.dao.role.RoleRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Instructor;
import com.example.OnlineCourse.entity.Role;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final RoleRepoJpa roleRepoJpa;
    private final JwtUtil jwtUtil;


    @Override
    public CreateInstructorRequestModel create(CreateInstructorRequestModel createInstructorRequestModel) {
        instructorRules.checkMail(createInstructorRequestModel.getEmail());
        Instructor instructor=modelMapperService.forRequest().map(createInstructorRequestModel,Instructor.class);
        Role role=roleRepoJpa.findById(createInstructorRequestModel.getRoleId()).orElse(null);
        if(role!=null){
            instructor.setRole(role);
        }
         boolean affectedRows= instructorRepo.create(instructor);
        if(affectedRows==true){
            CreateInstructorRequestModel createInstructorModel=modelMapperService.forRequest().map(instructor,CreateInstructorRequestModel.class);
            return createInstructorModel;
        }
        return null;

    }

    @Override
    public List<GetAllInstructorResponse> getAll() {
        List<Instructor> instructors=instructorRepoJpa.findAll();
        List<GetAllInstructorResponse>getAllInstructorResponses=instructors.stream().map(instructor -> {
            GetAllInstructorResponse getAllInstructorResponse=new GetAllInstructorResponse();
            getAllInstructorResponse.setName(instructor.getName());
            getAllInstructorResponse.setLastName(instructor.getLastName());
            getAllInstructorResponse.setEmail(instructor.getEmail());
            getAllInstructorResponse.setBirthDate(instructor.getBirthDate());
            getAllInstructorResponse.setDepartment(instructor.getDepartment());
            List<String>descriptions=instructor.getCourses().stream().map(Courses::getDescription).collect(Collectors.toList());
            getAllInstructorResponse.setCoursesGiven(descriptions);
            return getAllInstructorResponse;

        }).collect(Collectors.toList());
        return getAllInstructorResponses;


    }


    @Override
    public GetByIdInstructorResponse getById(int id,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        Instructor instructor = instructorRepoJpa.findById(id).orElse(null);
        if (instructor != null) {
            String role = jwtUtil.extractRole(token);
            if (role.equals("ROLE_ADMIN")) {
                GetByIdInstructorResponse getByIdInstructorResponse = new GetByIdInstructorResponse();
                getByIdInstructorResponse.setName(instructor.getName());
                getByIdInstructorResponse.setLastName(instructor.getLastName());
                getByIdInstructorResponse.setEmail(instructor.getEmail());
                getByIdInstructorResponse.setDepartment(instructor.getDepartment());
                getByIdInstructorResponse.setBirthDate(instructor.getBirthDate());
                List<String> courseGivens = instructor.getCourses().stream().map(Courses::getDescription).collect(Collectors.toList());
                getByIdInstructorResponse.setCoursesGiven(courseGivens);
                return getByIdInstructorResponse;
            } else if(!role.equals("ROLE_ADMIN")) {
                String insId = jwtUtil.extractUserId(token);
                Integer intId = Integer.parseInt(insId);
                if (id == intId) {
                    GetByIdInstructorResponse getByIdInstructorResponse = new GetByIdInstructorResponse();
                    getByIdInstructorResponse.setName(instructor.getName());
                    getByIdInstructorResponse.setLastName(instructor.getLastName());
                    getByIdInstructorResponse.setEmail(instructor.getEmail());
                    getByIdInstructorResponse.setDepartment(instructor.getDepartment());
                    getByIdInstructorResponse.setBirthDate(instructor.getBirthDate());
                    List<String> courseGivens = instructor.getCourses().stream().map(Courses::getDescription).collect(Collectors.toList());
                    getByIdInstructorResponse.setCoursesGiven(courseGivens);
                    return getByIdInstructorResponse;
                }else {
                    throw new BusinessExcepiton("İşlem Başarısız");
                }
                }
        }
        return null;
    }

    @Override
    public Boolean update(UpdateInstructorRequestModel updateInstructorRequestModel, int id,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String insId = jwtUtil.extractUserId(token);
        Integer intId = Integer.parseInt(insId);
        if(intId==id){
            instructorRules.checkOldPassword(id,updateInstructorRequestModel.getOldPassword());
            Instructor ins=modelMapperService.forRequest().map(updateInstructorRequestModel,Instructor.class);
            Boolean update=instructorRepo.update(ins,id);
            if(update==true){
                return true;
            }else {
                throw new BusinessExcepiton("Güncellemek istediğiniz kayıt bulunamadı");
                }
        }
        return false;
    }

    @Override
    public Boolean delete(int id,String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        String insId = jwtUtil.extractUserId(token);
        Integer intId = Integer.parseInt(insId);
        if(intId==id){
            Boolean delete=instructorRepo.delete(id);
            if(delete==true){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public TokenModel instructorLogin(CreateInstructorLoginRequestModel createInstructorLoginRequestModel) {
      Instructor instructor=instructorRepoJpa.findByEmail(createInstructorLoginRequestModel.getEmail()).orElse(null);
      if (instructor!=null&&passwordEncoder.matches(createInstructorLoginRequestModel.getPassword(),instructor.getPassword())){
          TokenModel tokenModel=new TokenModel();
          tokenModel.setUserId(String.valueOf(instructor.getId()));
          tokenModel.setUserName(instructor.getEmail());
          tokenModel.setRoleName(instructor.getRole().getRoleName());
          return tokenModel;

        }
      return null;


    }

}
