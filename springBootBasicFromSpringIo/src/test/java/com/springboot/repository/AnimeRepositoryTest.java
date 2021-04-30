package com.springboot.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot.domain.Anime;
import com.springboot.util.AnimeCreator;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2
class AnimeRepositoryTest {
	
	
	@Autowired
	private AnimeRepository animeRepository;

	@Test
	@DisplayName("Save persist anime when successful")
	void save_persistAnime_WhenSuccessful() {
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		Assertions.assertThat(animeSaved).isNotNull();
		Assertions.assertThat(animeSaved.getId()).isNotNull();
		Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
	}

	
	@Test
	@DisplayName("Save update anime when successful")
	void save_UpdateAnime_WhenSuccessful() {
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		animeSaved.setName("Test Name Updated");
		Anime animeUpdated = this.animeRepository.save(animeSaved);
		log.info("Save update anime when successful: animeUpdated.getName() = " + animeUpdated.getName());
		
		Assertions.assertThat(animeUpdated).isNotNull();
		Assertions.assertThat(animeUpdated.getId()).isNotNull();
		Assertions.assertThat(animeUpdated.getId()).isEqualTo(animeSaved.getId());
		Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
	}
	
	@Test
	@DisplayName("Delet anime when successful")
	void delete_RemoveAnime_WhenSuccessful() {
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		this.animeRepository.delete(animeSaved);
		
		Optional<Anime> findById = this.animeRepository.findById(animeSaved.getId());
		
		Assertions.assertThat(findById).isNotPresent();
	}
	
	
	@Test
	@DisplayName("Find by name return a list of anime when successful")
	void findByName_ReturnsListOfAnime_WhenSuccessful() {
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		String name = animeSaved.getName();
		
		
		List<Anime> animes = this.animeRepository.findByName(name);
		
		Assertions.assertThat(animes)
			.isNotEmpty()
			.contains(animeSaved);		
	}

	
	@Test
	@DisplayName("Find by name return a empty list of anime when no anime is found")
	void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
		List<Anime> animes = this.animeRepository.findByName("Name no exists");
		Assertions.assertThat(animes).isEmpty();		
	}
	
	
	@Test
	@DisplayName("Save throw ConstraintViolationException when name is empty")
	void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
		Anime anime = new Anime();
//		Assertions.assertThatThrownBy( () -> this.animeRepository.save(anime))
//				.isInstanceOf(ConstraintViolationException.class);
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
				.isThrownBy( () -> this.animeRepository.save(anime))
				.withMessageContaining("he anime name cannot be empty");
	}
	

}
