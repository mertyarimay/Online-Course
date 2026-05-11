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
import com.example.OnlineCourse.config.security.SecurityContextUser;
import com.example.OnlineCourse.dao.instructor.InstructorRepoJpa;
import com.example.OnlineCourse.dao.role.RoleRepoJpa;
import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.entity.Courses;
import com.example.OnlineCourse.entity.Instructor;
import com.example.OnlineCourse.entity.Role;
import com.example.OnlineCourse.entity.Users;
import com.example.OnlineCourse.exception.BadRequestException;
import com.example.OnlineCourse.exception.BusinessExcepiton;
import com.example.OnlineCourse.exception.ForbiddenException;
import com.example.OnlineCourse.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final ModelMapperService modelMapperService;
    private final InstructorRules instructorRules;
    private final InstructorRepoJpa instructorRepoJpa;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepoJpa roleRepoJpa;
    private final UsersRepoJpa usersRepoJpa;


    @Override
    @Transactional
    public CreateInstructorRequestModel create(CreateInstructorRequestModel request) {

        instructorRules.checkMail(request.getEmail());

        Instructor instructor = new Instructor();
        instructor.setName(request.getName());
        instructor.setLastName(request.getLastName());
        instructor.setEmail(request.getEmail());
        instructor.setDepartment(request.getDepartment());
        instructor.setPassword(passwordEncoder.encode(request.getPassword()));
        instructor.setBirthDate(request.getBirthDate());


        if (request.getUsersId() != null) {
            Users users = usersRepoJpa.findById(request.getUsersId()).orElse(null);
            if (users == null) {
                throw new NotFoundException("Böyle bir user Id yok");
            } else {
                instructor.setUsers(users);
            }
        } else {
            instructor.setUsers(null);
        }


        Role role = roleRepoJpa.findById(request.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role bulunamadı"));

        instructor.setRole(role);

        Instructor savedInstructor = instructorRepoJpa.save(instructor);

        CreateInstructorRequestModel createInstructorRequestModel = modelMapperService.forRequest().map(savedInstructor, CreateInstructorRequestModel.class);
        return createInstructorRequestModel;
    }

    @Override
    public List<GetAllInstructorResponse> getAll() {
        List<Instructor> instructors = instructorRepoJpa.findAll();
        List<GetAllInstructorResponse> getAllInstructorResponses = instructors.stream().map(instructor -> {
            GetAllInstructorResponse getAllInstructorResponse = new GetAllInstructorResponse();
            getAllInstructorResponse.setName(instructor.getName());
            getAllInstructorResponse.setLastName(instructor.getLastName());
            getAllInstructorResponse.setEmail(instructor.getEmail());
            getAllInstructorResponse.setBirthDate(instructor.getBirthDate());
            getAllInstructorResponse.setDepartment(instructor.getDepartment());
            List<String> descriptions = instructor.getCourses().stream().map(courses -> courses.getDescription()).collect(Collectors.toList());
            getAllInstructorResponse.setCoursesGiven(descriptions);
            return getAllInstructorResponse;

        }).collect(Collectors.toList());
        return getAllInstructorResponses;


    }


    @Override
    public GetByIdInstructorResponse getById(int id) {
        Instructor instructor = instructorRepoJpa.findById(id).orElse(null);
        if(instructor==null){
            throw new NotFoundException("Bu id ye ait kayıt bulunamadı");
        }
        String role = SecurityContextUser.getCurrentRole();
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
        } else if (!role.equals("ROLE_ADMIN")) {
            Integer intId = SecurityContextUser.getCurrentUserId();
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
            } else {
                throw new ForbiddenException("İşlem Başarısız");
            }
        }
        throw new ForbiddenException("İşlem Başarısız");
    }

    @Override
    @Transactional
    public Boolean update(UpdateInstructorRequestModel updateInstructorRequestModel, int id) {
        Integer intId = SecurityContextUser.getCurrentUserId();
        if (intId == id) {
            instructorRules.checkOldPassword(id, updateInstructorRequestModel.getOldPassword());
            Instructor instructor = instructorRepoJpa.findById(id).orElse(null);
            if (instructor == null) {
                throw new NotFoundException("Güncellemek istediğiniz kayıt bulunamadı");
            }
            instructor.setEmail(updateInstructorRequestModel.getEmail());
            instructor.setPassword(passwordEncoder.encode(updateInstructorRequestModel.getPassword()));
            Instructor updatedInstructor = instructorRepoJpa.save(instructor);
            if (updatedInstructor != null) {
                return true;
            } else {
               return false;
            }
        }
        throw new ForbiddenException("Yetkiniz olmayan bir kayıtta işlem yapamazssınız!!!!");
    }

    @Override
    @Transactional
    public Boolean delete(int id) {
        Integer intId = SecurityContextUser.getCurrentUserId();
        if (intId == id) {
            if (instructorRepoJpa.existsById(id)) {
                instructorRepoJpa.deleteById(id);
            }
            if (!instructorRepoJpa.existsById(id)) {
                return true;
            } else {
                return false;
            }
        }
        throw new ForbiddenException("Yetkisiz İşlem");
    }

    @Override
    public TokenModel instructorLogin(CreateInstructorLoginRequestModel createInstructorLoginRequestModel) {
        Instructor instructor = instructorRepoJpa.findByEmail(createInstructorLoginRequestModel.getEmail()).orElse(null);
        if (instructor != null && passwordEncoder.matches(createInstructorLoginRequestModel.getPassword(), instructor.getPassword())) {
            TokenModel tokenModel = new TokenModel();
            tokenModel.setUserId(String.valueOf(instructor.getId()));
            tokenModel.setUserName(instructor.getEmail());
            tokenModel.setRoleName(instructor.getRole().getRoleName());
            return tokenModel;

        }
        throw new BadRequestException("Email veya şifreniz hatalı");


    }

}
