package com.springboot.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.exceptions.BadRequestException;
import com.springboot.exceptions.BadRequestExceptionDetails;

//All the controllers must use what exist into this class basead in a flag (@ExceptionHandler)
@ControllerAdvice
public class RestExceptionHandler {
	
	//If a controller launch an exception of the type BadRequestException, must to execute this method
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
		return new ResponseEntity<>(
				BadRequestExceptionDetails.builder()
					.timestamp(LocalDateTime.now())
					.status(HttpStatus.BAD_REQUEST.value())
					.title("Bad Request Exception, Check the Documentation")
					.details(bre.getMessage())
					.developerMessage(bre.getClass().getName())
					.build(), HttpStatus.BAD_REQUEST);
	}

}
