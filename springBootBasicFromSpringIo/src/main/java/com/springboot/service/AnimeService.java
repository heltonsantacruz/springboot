package com.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.domain.Anime;
import com.springboot.exceptions.BadRequestException;
import com.springboot.mapper.AnimeMapper;
import com.springboot.repository.AnimeRepository;
import com.springbootrequests.AnimePostRequestBody;
import com.springbootrequests.AnimePutRequestBody;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimeService {
	
	
	//private static List<Anime> animes; 
	private final AnimeRepository animeRepository;
			
//	static {
//		animes = new ArrayList<>(List.of(new Anime(1L, "DB2"), new Anime(2L, "Berserk  uiuiui")));
//	}
	
	public List<Anime> listAll(){
		return animeRepository.findAll();
	}
	
	public List<Anime> findByName(String name){
		return animeRepository.findByName(name);
	}

//	public Anime findByIdOrThrowBadRequestException(long id) {
//		return animeRepository.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
//	}
	
	public Anime findByIdOrThrowBadRequestException(long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Anime not found"));
	}

	public Anime save(AnimePostRequestBody animePostRequestBody) {
		//return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
		return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
	}

	public void delete(long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
		
	}

	public void replace(AnimePutRequestBody animePutRequestBody) {
		Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
		Anime anime = animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePutRequestBody));
		anime.setId(savedAnime.getId());
//		Anime anime = Anime.builder()
//				.id(savedAnime.getId())
//				.name(animePutRequestBody.getName())
//				.build();
		animeRepository.save(anime);
		
	}

}
