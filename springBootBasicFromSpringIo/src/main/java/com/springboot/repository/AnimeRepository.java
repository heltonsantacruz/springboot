package com.springboot.repository;

import java.util.List;

import com.springboot.domain.Anime;

public interface AnimeRepository {
	
	List<Anime> listAll();

}
