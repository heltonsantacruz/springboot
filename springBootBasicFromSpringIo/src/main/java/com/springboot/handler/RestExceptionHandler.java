package com.springboot.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.exceptions.BadRequestException;
import com.springboot.exceptions.BadRequestExceptionDetails;
import com.springboot.exceptions.ExceptionDetails;
import com.springboot.exceptions.ValidationExceptionDetails;

import lombok.extern.log4j.Log4j2;

//All the controllers must use what exist into this class basead in a flag (@ExceptionHandler)
@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	//If a controller launch an exception of the type BadRequestException, must to execute this method
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre){
		return new ResponseEntity<>(
				BadRequestExceptionDetails.builder()
					.timestamp(LocalDateTime.now())
					.status(HttpStatus.BAD_REQUEST.value())
					.title("Bad Request Exception, Check the Documentation")
					.details(bre.getMessage())
					.developerMessage(bre.getClass().getName())
					.build(), HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		//log.info("Fileds {}", exception.getBindingResult().getFieldError().getField());
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		return new ResponseEntity<>(
				ValidationExceptionDetails.builder()
					.timestamp(LocalDateTime.now())
					.status(HttpStatus.BAD_REQUEST.value())
					.title("Bad Request Exception, Invalid Fields")
					.details(exception.getMessage())
					.developerMessage(exception.getClass().getName())
					.fields(fields)
					.fieldsMessage(fieldsMessage)
					.build(), HttpStatus.BAD_REQUEST);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
			.timestamp(LocalDateTime.now())
			.status(status.value())
			.title(ex.getCause().getMessage())
			.details(ex.getMessage())
			.developerMessage(ex.getClass().getName())
			.build();
			
			
		return new ResponseEntity<>(exceptionDetails, headers, status);
	}

}
