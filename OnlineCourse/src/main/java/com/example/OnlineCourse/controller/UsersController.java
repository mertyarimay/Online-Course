package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateUsersRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateUsersRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllUsersResponse;
import com.example.OnlineCourse.business.model.response.GetByIdUsersResponse;
import com.example.OnlineCourse.business.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;


    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody @Valid CreateUsersRequestModel createUsersRequestModel){
        CreateUsersRequestModel createUsersRequestModel1=usersService.create(createUsersRequestModel);
        if(createUsersRequestModel1!=null){
            return ResponseEntity.ok("Kayıt İşleminiz Başarılı bir Şekilde Gerçekleşmiştir.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt işleminiz BAŞARISIZ Olmuştur");
        }
    }
    @GetMapping
    public List<GetAllUsersResponse>getAll(Optional<Integer>coursesId){
        List<GetAllUsersResponse>getAllUsersResponses=usersService.getAll(coursesId);
        return getAllUsersResponses;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        GetByIdUsersResponse getByIdUsersResponse=usersService.getById(id);
        if(getByIdUsersResponse!=null){
            return ResponseEntity.ok(getByIdUsersResponse);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ıdye ait kayıt bulunamadı .");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody  UpdateUsersRequestModel updateUsersRequestModel,@PathVariable ("id") int id ){
        UpdateUsersRequestModel updateUsersRequestModel1=usersService.update(updateUsersRequestModel,id);
        if (updateUsersRequestModel1!=null){
          return   ResponseEntity.ok("Güncelleme işleminiz başarılı bir şekilde gerçekleşti.");
        }
        else {
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id YE Ait kayıt bulunamadı güncelleme işlemi başarısız");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id")int id){
        Boolean delete=usersService.delete(id);
        if (delete!=false){
          return   ResponseEntity.ok("Kaydınız Başarılı Bir Şekilde Silindi.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silmek istediğiniz Id mevcut değildir  Silme işlemi BAŞARISIZ.");
        }
    }
}
