package com.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.domain.Anime;
import com.springboot.service.AnimeService;
import com.springboot.util.DateUtil;
import com.springbootrequests.AnimePostRequestBody;
import com.springbootrequests.AnimePutRequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor 
public class AnimeController {
		
	private final DateUtil dateUtil;
	private final AnimeService animeService;
	
/* Without pages */
	//localhost:8080/animes/list
	//@GetMapping(path="list")
//	@GetMapping
//	public ResponseEntity<List<Anime>> list(){
//		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
//		return ResponseEntity.ok(animeService.listAll());
//	}
	
/* With pages*/ 	
	@GetMapping
	public ResponseEntity<Page<Anime>> list(Pageable pageable){
		//log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok(animeService.listAll(pageable));
	}

	
	@GetMapping(path = "/all")
	public ResponseEntity<List<Anime>> listAll(){
		//log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok(animeService.listAllNonPageable());
	}

	
	@GetMapping(path="/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}

//  Option that works but it is not the better. Beloow follow the better	
//	@GetMapping(path="/find/{name}")
//	public ResponseEntity<List<Anime>> findByName(@PathVariable String name){
//		return ResponseEntity.ok(animeService.findByName(name));
//	}
	
	@GetMapping(path="/find")
	//public ResponseEntity<List<Anime>> findByName(@RequestParam(name = "name") String name){
	//public ResponseEntity<List<Anime>> findByName(@RequestParam(required = false) String name){
	//public ResponseEntity<List<Anime>> findByName(@RequestParam(required = false) String name, @RequestParam(required = false) String title){
	//Default = @RequestParam(required = true)
	public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
		return ResponseEntity.ok(animeService.findByName(name));
	}

	
	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
		return new ResponseEntity<>(animeService.save(animePostRequestBody),HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody @Valid AnimePutRequestBody animePutRequestBody){
		animeService.replace(animePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
