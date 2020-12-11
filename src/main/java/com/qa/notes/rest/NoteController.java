package com.qa.notes.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.notes.dto.NoteDTO;
import com.qa.notes.persistence.domain.Note;
import com.qa.notes.service.NoteService;

@RestController
@CrossOrigin
@RequestMapping("/notes")
public class NoteController {
	
	private NoteService service;
	
	@Autowired
	public NoteController(NoteService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("")
	public ResponseEntity<NoteDTO> create(@RequestBody Note note) {
		NoteDTO created = this.service.create(note);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public ResponseEntity<List<NoteDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NoteDTO> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<NoteDTO> update(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
		return new ResponseEntity<>(this.service.update(noteDTO, id), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<NoteDTO> delete(@PathVariable Long id) {
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
