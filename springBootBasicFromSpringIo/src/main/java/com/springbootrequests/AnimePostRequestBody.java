package com.springbootrequests;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
	
	@NotEmpty(message = "The anime name cannot be empty")	
	private String name;
	
	
//	@URL(message = "The URL is not valid")
//	private String url;


}
