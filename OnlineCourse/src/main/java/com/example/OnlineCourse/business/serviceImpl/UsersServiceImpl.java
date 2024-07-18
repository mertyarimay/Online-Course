package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateUsersLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateUsersRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllUsersResponse;
import com.example.OnlineCourse.business.model.response.GetByIdUsersResponse;
import com.example.OnlineCourse.business.rules.UsersRules;
import com.example.OnlineCourse.business.service.UsersService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepoJpa usersRepoJpa;
    private final ModelMapperService modelMapperService;
    private final UsersRules usersRules;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUsersRequestModel create(CreateUsersRequestModel createUsersRequestModel) {
        usersRules.existByTckmlkNo(createUsersRequestModel.getTckmlkNo());
        Users users=new Users();
        users.setName(createUsersRequestModel.getName());
        users.setLastName(createUsersRequestModel.getLastName());
        users.setTckmlkNo(createUsersRequestModel.getTckmlkNo());
        users.setEmail(createUsersRequestModel.getEmail());
        users.setBirthDate(createUsersRequestModel.getBirthDate());
        users.setPassword(passwordEncoder.encode(createUsersRequestModel.getPassword()));
        CreateUsersRequestModel createUsersRequestModel1=modelMapperService.forRequest().map(usersRepoJpa.save(users),CreateUsersRequestModel.class);
        return createUsersRequestModel1;
    }

    @Override
    public List<GetAllUsersResponse> getAll() {
        List<Users>users=usersRepoJpa.findAll();
        List<GetAllUsersResponse>getAllUsersResponses=users.stream()
                .map(users1 -> modelMapperService.forResponse()
                        .map(users1, GetAllUsersResponse.class)).collect(Collectors.toList());
        return getAllUsersResponses;

    }

    @Override
    public GetByIdUsersResponse getById(int id) {
        Users users=usersRepoJpa.findById(id).orElse(null);
        if (users!=null){
            GetByIdUsersResponse getByIdUsersResponse=modelMapperService.forResponse()
                    .map(users,GetByIdUsersResponse.class);
            return getByIdUsersResponse;
        }else {
            return null;
        }
    }

    @Override
    public UpdateUsersRequestModel update(UpdateUsersRequestModel updateUsersRequestModel, int id) {
        Optional<Users>users=usersRepoJpa.findById(id);
        if (users.isPresent()){
           // usersRules.existByEmail(updateUsersRequestModel.getEmail());
            usersRules.checkOldPassword(id,updateUsersRequestModel.getOldPassword());
            users.get().setEmail(updateUsersRequestModel.getEmail());
            users.get().setPassword(passwordEncoder.encode(updateUsersRequestModel.getPassword()));
            UpdateUsersRequestModel updateUsersRequestModel1=modelMapperService.forRequest()
                    .map(usersRepoJpa.save(users.get()),UpdateUsersRequestModel.class);
            return updateUsersRequestModel1;
        }else {
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {

        Users users=usersRepoJpa.findById(id).orElse(null);
       if (users!=null){
           usersRepoJpa.deleteById(id);
           return true;
       }else {
           return false;
       }

    }

    @Override
    public Boolean authenticateUser(CreateUsersLoginRequestModel createUsersLoginRequestModel) {
        Users users=usersRepoJpa.findByEmail(createUsersLoginRequestModel.getEmail()).orElse(null);
        if(users!=null&&passwordEncoder.matches(createUsersLoginRequestModel.getPassword(),users.getPassword())){
            return true;
        }
        else {
            return false;
        }

    }
}
