package com.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.domain.Anime;
import com.springboot.util.DateUtil;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("anime")
@Log4j2
//@AllArgsConstructor
public class AnimeController {
		
	private DateUtil dateUtil;
	
	
	public AnimeController(DateUtil dateUtil) {
		this.dateUtil = dateUtil;
	}

	//localhost:8080/anime/list
	@GetMapping(path="list")
	public List<Anime> list(){
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return List.of(new Anime("DB2"), new Anime("Berserk  uiuiui"));
	}

}
