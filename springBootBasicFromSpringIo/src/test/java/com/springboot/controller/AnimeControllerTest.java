package com.springboot.controller;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot.domain.Anime;
import com.springboot.service.AnimeService;
import com.springboot.util.AnimeCreator;
import com.springboot.util.AnimePostRequestBodyCreator;
import com.springboot.util.AnimePutRequestBodyCreator;
import com.springbootrequests.AnimePostRequestBody;
import com.springbootrequests.AnimePutRequestBody;


@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
	
	@InjectMocks
	private AnimeController animeControllerMock;
	
	@Mock
	private AnimeService animeServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
				.thenReturn(animePage);
		
		BDDMockito.when(animeServiceMock.listAllNonPageable())
			.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
			.thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
			.thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));
		
		BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
	}
	

	@Test
	@DisplayName("List returns list of anime inside page object when successful")
	 void list_ReturnListOfAnimesInsidePageObject_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		Page<Anime> animePage = animeControllerMock.list(null).getBody();
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}
	
	
	@Test
	@DisplayName("List returns list of anime when successful")
	 void list_ReturnListOfAnimes_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		List<Anime> animes = animeControllerMock.listAll().getBody();
		Assertions.assertThat(animes).isNotNull();
		Assertions.assertThat(animes)
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findById returns an anime when successful")
	 void findById_ReturnAnime_WhenSuccessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		Anime anime = animeControllerMock.findById(1).getBody();
		Assertions.assertThat(anime).isNotNull();
		Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findByName returns a list of animes when successful")
	 void findByName_ReturnLisOfAnimes_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		List<Anime> animes = animeControllerMock.findByName("Test").getBody();
		Assertions.assertThat(animes).isNotNull();
		Assertions.assertThat(animes)
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findByName returns an empty list of animes when anime is not found")
	 void findByName_ReturnAnEmptyList_WhenAnimeNotFound() {
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(Collections.emptyList());
		
		List<Anime> animes = animeControllerMock.findByName("Test").getBody();
		Assertions.assertThat(animes)
			.isNotNull()
			.isEmpty();
	}
	
	
	
	@Test
	@DisplayName("save returns an anime when successful")
	 void save_ReturnAnime_WhenSuccessful() {
		Anime anime = animeControllerMock.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
		Assertions.assertThat(anime)
			.isNotNull()
			.isEqualTo(AnimeCreator.createValidAnime());
	}

	
	@Test
	@DisplayName("replace update an anime when successful")
	 void replace_UpdateAnime_WhenSuccessful() {
		Assertions.assertThatCode(() -> animeControllerMock.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
			.doesNotThrowAnyException();
		
		
		ResponseEntity<Void> entity = animeControllerMock.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	@DisplayName("delete removes an anime when successful")
	 void delete_RemovesAnime_WhenSuccessful() {
		Assertions.assertThatCode(() -> animeControllerMock.delete(1L))
			.doesNotThrowAnyException();
		
		
		ResponseEntity<Void> entity = animeControllerMock.delete(1L);
		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

}
