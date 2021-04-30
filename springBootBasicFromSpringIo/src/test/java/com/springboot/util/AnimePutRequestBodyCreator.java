package com.springboot.util;

import com.springbootrequests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
	
	public static AnimePutRequestBody createAnimePutRequestBody() {
		return AnimePutRequestBody.builder()
				.name(AnimeCreator.createValidUpdatedAnime().getName())
				.id(AnimeCreator.createValidUpdatedAnime().getId())
				.build();
	}
	

}
