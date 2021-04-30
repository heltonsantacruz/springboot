package com.springboot.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot.domain.Anime;
import com.springboot.exceptions.BadRequestException;
import com.springboot.repository.AnimeRepository;
import com.springboot.util.AnimeCreator;
import com.springboot.util.AnimePostRequestBodyCreator;
import com.springboot.util.AnimePutRequestBodyCreator;


@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

	@InjectMocks
	private AnimeService animeServiceMock;
	
	@Mock
	private AnimeRepository animeRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
				.thenReturn(animePage);
		
		BDDMockito.when(animeRepositoryMock.findAll())
			.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
			.thenReturn(AnimeCreator.createValidAnime());
		
		
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
	}
	

	@Test
	@DisplayName("listAll returns list of anime inside page object when successful")
	 void list_ReturnListOfAnimesInsidePageObject_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		Page<Anime> animePage = animeServiceMock.listAll(PageRequest.of(1,1));
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}
	
	
	@Test
	@DisplayName("listAllNonPageable returns list of anime when successful")
	 void list_ReturnListOfAnimes_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		List<Anime> animes = animeServiceMock.listAllNonPageable();
		Assertions.assertThat(animes)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException returns an anime when successful")
	 void findById_ReturnAnime_WhenSuccessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		Anime anime = animeServiceMock.findByIdOrThrowBadRequestException(1);
		Assertions.assertThat(anime).isNotNull();
		Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
	}
	
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
	 void findById_ThrowsBadRequestException_WhenAnimeIsNotFound() {
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
			.thenReturn(Optional.empty());
		Assertions.assertThatExceptionOfType(BadRequestException.class)
			.isThrownBy(() -> animeServiceMock.findByIdOrThrowBadRequestException(1));
	}
	
	@Test
	@DisplayName("findByName returns a list of animes when successful")
	 void findByName_ReturnLisOfAnimes_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		List<Anime> animes = animeServiceMock.findByName("Test");
		Assertions.assertThat(animes)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
	}
	
	@Test
	@DisplayName("findByName returns an empty list of animes when anime is not found")
	 void findByName_ReturnAnEmptyList_WhenAnimeNotFound() {
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
			.thenReturn(Collections.emptyList());
		
		List<Anime> animes = animeServiceMock.findByName("Test");
		Assertions.assertThat(animes)
			.isNotNull()
			.isEmpty();
	}
	
	
	
	@Test
	@DisplayName("save returns an anime when successful")
	 void save_ReturnAnime_WhenSuccessful() {
		Anime anime = animeServiceMock.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());
		Assertions.assertThat(anime)
			.isNotNull()
			.isEqualTo(AnimeCreator.createValidAnime());
	}

	
	@Test
	@DisplayName("replace update an anime when successful")
	 void replace_UpdateAnime_WhenSuccessful() {
		Assertions.assertThatCode(() -> animeServiceMock.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
			.doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("delete removes an anime when successful")
	 void delete_RemovesAnime_WhenSuccessful() {
		Assertions.assertThatCode(() -> animeServiceMock.delete(1L))
			.doesNotThrowAnyException();
	}


}
