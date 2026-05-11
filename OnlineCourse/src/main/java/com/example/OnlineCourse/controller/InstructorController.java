package com.example.OnlineCourse.controller;

import com.example.OnlineCourse.business.model.request.CreateInstructorLoginRequestModel;
import com.example.OnlineCourse.business.model.request.CreateInstructorRequestModel;
import com.example.OnlineCourse.business.model.request.UpdateInstructorRequestModel;
import com.example.OnlineCourse.business.model.response.GetAllInstructorResponse;
import com.example.OnlineCourse.business.model.response.GetByIdInstructorResponse;
import com.example.OnlineCourse.business.model.response.TokenModel;
import com.example.OnlineCourse.business.service.InstructorService;
import com.example.OnlineCourse.config.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/instructor")

public class InstructorController {
    private final InstructorService instructorService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateInstructorRequestModel createInstructorRequestModel) {
        CreateInstructorRequestModel createInstructorModel = instructorService.create(createInstructorRequestModel);
         return ResponseEntity.ok(createInstructorModel);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<GetAllInstructorResponse> getAll() {
        List<GetAllInstructorResponse> getAllInstructorResponse = instructorService.getAll();
        return getAllInstructorResponse;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR', 'ROLE_ADMIN')")
    public ResponseEntity<Object> getById(@PathVariable("id") int id) {
        GetByIdInstructorResponse getByIdInstructorResponse = instructorService.getById(id);
        if (getByIdInstructorResponse != null) {
            return ResponseEntity.ok(getByIdInstructorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu Id ye ait kayıt bulunamamıştır");
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateInstructorRequestModel updateInstructorRequestModel, @PathVariable("id") int id) {
        Boolean update = instructorService.update(updateInstructorRequestModel, id);
        if (update == true) {
            return ResponseEntity.ok("Şifre Güncelleme İşlemi Başarılı");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Güncelleme İşlemi Başarısız...");
        }

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_INSTRUCTOR')")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Boolean delete = instructorService.delete(id);
        if (delete == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Silme İşlemi Başarısız");
        } else {
            return ResponseEntity.ok("Silme İşlemi BAŞARILI");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid CreateInstructorLoginRequestModel createInstructorLoginRequestModel) {
        TokenModel tokenModel = instructorService.instructorLogin(createInstructorLoginRequestModel);
        if (tokenModel != null) {
            return jwtUtil.generateToken(tokenModel.getUserName(), tokenModel.getUserId(), tokenModel.getRoleName());
        } else {
            String hata = "Email veya şifreniz hatalı";
            return hata;
        }

    }


}
