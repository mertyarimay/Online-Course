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
        usersRules.existByEmail(createUsersRequestModel.getEmail());
        Users users=new Users();
        users.setName(createUsersRequestModel.getName());
        users.setLastName(createUsersRequestModel.getLastName());
        users.setTckmlkNo(createUsersRequestModel.getTckmlkNo());
        users.setEmail(createUsersRequestModel.getEmail());
        users.setBirthDate(createUsersRequestModel.getBirthDate());
        users.setPassword(passwordEncoder.encode(createUsersRequestModel.getPassword()));
        CreateUsersRequestModel createUserModel=modelMapperService.forRequest().map(usersRepoJpa.save(users),CreateUsersRequestModel.class);
        return createUserModel;
    }

    @Override
    public List<GetAllUsersResponse> getAll() {
        List<Users>users=usersRepoJpa.findAll();
        List<GetAllUsersResponse>getAllUsersResponses=users.stream()
                .map(user -> modelMapperService.forResponse()
                        .map(user, GetAllUsersResponse.class)).collect(Collectors.toList());
        return getAllUsersResponses;

    }

    @Override
    public GetByIdUsersResponse getById(int id) {
        Users user=usersRepoJpa.findById(id).orElse(null);
        if (user!=null){
            GetByIdUsersResponse getByIdUsersResponse=modelMapperService.forResponse()
                    .map(user,GetByIdUsersResponse.class);
            return getByIdUsersResponse;
        }else {
            return null;
        }
    }

    @Override
    public UpdateUsersRequestModel update(UpdateUsersRequestModel updateUsersRequestModel, int id) {
        Optional<Users>user=usersRepoJpa.findById(id);
        if (user.isPresent()){
           // usersRules.existByEmail(updateUsersRequestModel.getEmail());
            usersRules.checkOldPassword(id,updateUsersRequestModel.getOldPassword());
            user.get().setEmail(updateUsersRequestModel.getEmail());
            user.get().setPassword(passwordEncoder.encode(updateUsersRequestModel.getPassword()));
            UpdateUsersRequestModel updateModel=modelMapperService.forRequest()
                    .map(usersRepoJpa.save(user.get()),UpdateUsersRequestModel.class);
            return updateModel;
        }else {
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {

        Users user=usersRepoJpa.findById(id).orElse(null);
       if (user!=null){
           usersRepoJpa.deleteById(id);
           return true;
       }else {
           return false;
       }

    }

    @Override
    public Boolean authenticateUser(CreateUsersLoginRequestModel createUsersLoginRequestModel) {
        Users user=usersRepoJpa.findByEmail(createUsersLoginRequestModel.getEmail()).orElse(null);
        if(user!=null&&passwordEncoder.matches(createUsersLoginRequestModel.getPassword(),user.getPassword())){
            return true;
        }
        else {
            return false;
        }

    }
}
