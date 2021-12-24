package com.paulo.os.resource.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paulo.os.service.exceptions.DataIntegratyViolationException;
import com.paulo.os.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandarError> objectNotFoundException(ObjectNotFoundException e){
		StandarError error = new StandarError(System.currentTimeMillis(),
					HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
		
	
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandarError> objectNotFoundException(DataIntegratyViolationException e){
		StandarError error = new StandarError(System.currentTimeMillis(),
					HttpStatus.BAD_REQUEST.value(), e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> objectNotFoundException(MethodArgumentNotValidException e){
		
		ValidationError error = new ValidationError (System.currentTimeMillis()
				,HttpStatus.BAD_REQUEST.value(), "Erro na validação dos campos");
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			error.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
		
}
