package com.example.OnlineCourse.business.serviceImpl;

import com.example.OnlineCourse.business.model.request.CreateUsersLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateUsersRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllUsersResponse;
import com.example.OnlineCourse.business.model.response.GetByIdUsersResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.rules.UsersRules;
import com.example.OnlineCourse.business.service.UsersService;
import com.example.OnlineCourse.config.mapper.ModelMapperService;
import com.example.OnlineCourse.config.security.SecurityContextUser;
import com.example.OnlineCourse.dao.role.RoleRepoJpa;
import com.example.OnlineCourse.dao.users.UsersRepoJpa;
import com.example.OnlineCourse.entity.Role;
import com.example.OnlineCourse.entity.Users;
import com.example.OnlineCourse.exception.BadRequestException;
import com.example.OnlineCourse.exception.ForbiddenException;
import com.example.OnlineCourse.exception.NotFoundException;
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
    private final RoleRepoJpa roleRepoJpa;

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
        Role role=roleRepoJpa.findById(createUsersRequestModel.getRoleId()).orElse(null);
        if(role!=null){
            users.setRole(role);
        }else{
            throw new NotFoundException("Role Id Bulunamadı");
        }
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
        int userId = SecurityContextUser.getCurrentUserId();
        String role = SecurityContextUser.getCurrentRole();

        if(role.equals("ROLE_ADMIN")){
            Users user=usersRepoJpa.findById(id).orElse(null);
            if(user==null){
                throw new NotFoundException("Kayıt Bulunamadı");
            }
            GetByIdUsersResponse getByIdUsersResponse=modelMapperService.forResponse().map(user,GetByIdUsersResponse.class);
            return getByIdUsersResponse;
        } else if (role.equals("ROLE_USER")) {
            if(userId==id){
                Users user=usersRepoJpa.findById(id).orElse(null);
                if(user==null){
                    throw new NotFoundException("Kayıt Bulunamadı");
                }
                GetByIdUsersResponse getByIdUsersResponse=modelMapperService.forResponse().map(user,GetByIdUsersResponse.class);
                return getByIdUsersResponse;
            }else{
                throw new ForbiddenException("İşlem Başarısız!!!");
            }
        }
        throw new ForbiddenException("İşlem Başarısız!!!");
    }

    @Override
    public UpdateUsersRequestModel update(UpdateUsersRequestModel updateUsersRequestModel, int id) {
        int userId = SecurityContextUser.getCurrentUserId();
        Optional<Users>user=usersRepoJpa.findById(id);
        if (user.isPresent()&&userId==id){
            usersRules.checkOldPassword(id,updateUsersRequestModel.getOldPassword());
            user.get().setEmail(updateUsersRequestModel.getEmail());
            user.get().setPassword(passwordEncoder.encode(updateUsersRequestModel.getPassword()));
            UpdateUsersRequestModel updateModel=modelMapperService.forRequest()
                    .map(usersRepoJpa.save(user.get()),UpdateUsersRequestModel.class);
            return updateModel;
        }else {
            throw new ForbiddenException("İşlem Başarısızz!!!");
        }
    }

    @Override
    public Boolean delete(int id) {
        int userId = SecurityContextUser.getCurrentUserId();
        Users user=usersRepoJpa.findById(id).orElse(null);
        if(user==null){
            throw new NotFoundException("Silme İşlemi Başarısız");
        }

       if (userId!=id){
           throw new ForbiddenException("Yetkisiz İşlem Silme İşlemi Başarısız!!!");
       }
       usersRepoJpa.deleteById(id);
       return !usersRepoJpa.existsById(id);

    }

    @Override
    public TokenModel authenticateUser(CreateUsersLoginRequestModel createUsersLoginRequestModel) {
        Users user=usersRepoJpa.findByEmail(createUsersLoginRequestModel.getEmail()).orElse(null);
        if(user!=null&&passwordEncoder.matches(createUsersLoginRequestModel.getPassword(),user.getPassword())){
            TokenModel tokenModel=new TokenModel();
            tokenModel.setUserId(String.valueOf(user.getId()));
            tokenModel.setUserName(user.getEmail());
            tokenModel.setRoleName(user.getRole().getRoleName());

            return tokenModel;
        }
        else {
            throw new BadRequestException("Geçersiz Email veya Şifre Girdiniz");

        }

    }
}
