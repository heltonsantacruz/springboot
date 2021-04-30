package com.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // annotation of lombok that generate get and set
@AllArgsConstructor //annotation of lombok that generate contructor with all arguments
@NoArgsConstructor
@Entity
@Builder
public class Anime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "The anime name cannot be empty")
	private String name;

}
