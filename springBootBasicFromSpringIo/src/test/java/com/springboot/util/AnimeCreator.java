package com.springboot.util;

import com.springboot.domain.Anime;

public class AnimeCreator {
	
	public static Anime createAnimeToBeSaved() {
		return Anime.builder()
				.name("Test 1")
				.build();
	}
	
	
	public static Anime createValidAnime() {
		return Anime.builder()
				.name("Test 1")
				.id(1L)
				.build();
	}
	
	public static Anime createValidUpdatedAnime() {
		return Anime.builder()
				.name("Test 1 Updated")
				.id(1L)
				.build();
	}

}
