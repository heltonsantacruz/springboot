package com.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // annotation of lombok that generate get and set
@AllArgsConstructor //annotation of lombok that generate contructor with all arguments
public class Anime {
	
	private Long id;
	private String name;

}
