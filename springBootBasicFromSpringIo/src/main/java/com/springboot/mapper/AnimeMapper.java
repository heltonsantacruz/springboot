package com.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.springboot.domain.Anime;
import com.springbootrequests.AnimePostRequestBody;
import com.springbootrequests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public interface AnimeMapper {
	
	public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
	
	public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
	
	public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);

}
