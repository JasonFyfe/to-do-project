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

import com.qa.notes.dto.TodoDTO;
import com.qa.notes.persistence.domain.Todo;
import com.qa.notes.service.TodoService;

@RestController
@CrossOrigin
@RequestMapping("/todos")
public class TodoController {
	
	private TodoService service;
	
	@Autowired
	public TodoController(TodoService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("")
	public ResponseEntity<TodoDTO> create(@RequestBody Todo todo) {
		TodoDTO created = this.service.create(todo);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public ResponseEntity<List<TodoDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TodoDTO> readOne(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TodoDTO> update(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {
		return new ResponseEntity<>(this.service.update(todoDTO, id), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TodoDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
