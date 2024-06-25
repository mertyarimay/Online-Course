package com.example.OnlineCourse;

import com.example.OnlineCourse.exception.BusinessExcepiton;
import com.example.OnlineCourse.exception.ProblemDetails;
import com.example.OnlineCourse.exception.ValidationProblems;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@SpringBootApplication
public class OnlineCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineCourseApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}


	@ExceptionHandler
	@ResponseStatus(code= HttpStatus.BAD_REQUEST)
	public ProblemDetails validationProblems(MethodArgumentNotValidException methodArgumentNotValidException){
		ValidationProblems validationProblems=new ValidationProblems();
		validationProblems.setMessage("Validation Exception");
		validationProblems.setValidationErrors(new HashMap<String,String>());
		for (FieldError fieldError:methodArgumentNotValidException.getBindingResult().getFieldErrors()){
			validationProblems.getValidationErrors().put(fieldError.getField(),fieldError.getDefaultMessage());
		}
		return validationProblems;

	}
	@ExceptionHandler
	@ResponseStatus(code= HttpStatus.BAD_REQUEST)
	public ProblemDetails handleBusinessException(BusinessExcepiton businessExcepiton){
		ProblemDetails problemDetails=new ProblemDetails();
		problemDetails.setMessage(businessExcepiton.getMessage());
		return problemDetails;
	}

}
