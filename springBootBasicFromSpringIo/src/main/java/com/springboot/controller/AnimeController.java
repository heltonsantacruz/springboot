package com.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.domain.Anime;
import com.springboot.service.AnimeService;
import com.springboot.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor 
public class AnimeController {
		
	private final DateUtil dateUtil;
	private final AnimeService animeService;
	

	//localhost:8080/animes/list
	//@GetMapping(path="list")
	@GetMapping
	public List<Anime> list(){
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return animeService.listAll();
	}

}
