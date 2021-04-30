package com.springboot.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ExceptionDetails {
	
	protected String title;
	protected int status;
	protected String details;
	protected String developerMessage;
	protected LocalDateTime timestamp;

}
