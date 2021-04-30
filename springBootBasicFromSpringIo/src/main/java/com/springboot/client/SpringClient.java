package com.springboot.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.springboot.domain.Anime;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient {
	
	public static void main(String[] args) {
		ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/1", Anime.class);
		log.info(entity);
		 
		Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/1", Anime.class);
		log.info(object);
		
		Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
		log.info(Arrays.toString(animes));
		
		
		ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>() {});
		log.info(exchange.getBody());
		
//		Anime x = Anime.builder().name("Giu").build();		
//		Anime postForObject = new RestTemplate().postForObject("http://localhost:8080/animes", x, Anime.class);
//		log.info(postForObject);
		
//		Anime x = Anime.builder().name("John ").build();		
//		ResponseEntity<Anime> exchange2 = new RestTemplate().exchange("http://localhost:8080/animes", 
//				HttpMethod.POST, 
//				new HttpEntity<>(x),
//				Anime.class);
//		log.info(exchange2);
		
//		Anime x2 = Anime.builder().name("John 2").build();		
//		ResponseEntity<Anime> exchange3 = new RestTemplate().exchange("http://localhost:8080/animes", 
//				HttpMethod.POST, 
//				new HttpEntity<>(x2, createJsonHeader()),
//				Anime.class);
//		log.info(exchange3);
		
		Anime x2 = Anime.builder().name("John 4").build();		
		ResponseEntity<Anime> exchange3 = new RestTemplate().exchange("http://localhost:8080/animes", 
				HttpMethod.POST, 
				new HttpEntity<>(x2, createJsonHeader()),
				Anime.class);
		log.info(exchange3);
		
		Anime animeToBeUpdated = exchange3.getBody();
		animeToBeUpdated.setName("John 4 Updated");
		ResponseEntity<Void> exchange4 = new RestTemplate().exchange("http://localhost:8080/animes", 
				HttpMethod.PUT, 
				new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
				Void.class);
		log.info(exchange4);
		
		
		ResponseEntity<Void> exchange5 = new RestTemplate().exchange("http://localhost:8080/animes/{id}", 
				HttpMethod.DELETE, 
				null,
				Void.class,
				animeToBeUpdated.getId());
		log.info(exchange5);
		
		
		
	}
	
	
	private static HttpHeaders createJsonHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

}
