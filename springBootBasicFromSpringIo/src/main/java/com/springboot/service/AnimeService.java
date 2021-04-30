package com.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	/* Without pages*/
//	public List<Anime> listAll(){
//		return animeRepository.findAll();
//	}
	
	
	/* With pages*/
	public Page<Anime> listAll(Pageable pageable){
		return animeRepository.findAll(pageable);
	}

	
	public List<Anime> listAllNonPageable() {
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

	//The spring will commit nothing while the method don't finish ou if trhow any exception
	//Transaction by default doesn't consider checked exception. To work this it is necessary put the parameter @Transactional(rollbackOn = Exception.class) 
	@Transactional
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
//		anime.setId(savedAnime.getId());
//		Anime anime = Anime.builder()
//				.id(savedAnime.getId())
//				.name(animePutRequestBody.getName())
//				.build();
//		animeRepository.save(anime);
		
	}


}
