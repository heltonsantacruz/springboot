package com.springboot.util;

import com.springbootrequests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
	
	public static AnimePostRequestBody createAnimePostRequestBody() {
		return AnimePostRequestBody.builder()
				.name(AnimeCreator.createAnimeToBeSaved().getName())
				.build();
	}
	

}
