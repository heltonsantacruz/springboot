package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.domain.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long>{
	
	List<Anime> findByName(String name);

}
