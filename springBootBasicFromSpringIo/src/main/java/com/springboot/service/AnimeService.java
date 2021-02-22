package com.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.domain.Anime;

@Service
public class AnimeService {
	
	public List<Anime> listAll(){
		return List.of(new Anime(1L, "DB2"), new Anime(2L, "Berserk  uiuiui"));
	}

}
