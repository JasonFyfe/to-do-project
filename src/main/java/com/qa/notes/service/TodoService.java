package com.qa.notes.service;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.notes.dto.TodoDTO;
import com.qa.notes.exceptions.TodoNotFoundException;
import com.qa.notes.persistence.domain.Todo;
import com.qa.notes.persistence.repo.TodoRepo;
import com.qa.notes.util.SpringBeanUtil;

@Service
public class TodoService {
	private TodoRepo repo;
	
	private ModelMapper mapper;
	
	private TodoDTO mapToDTO(Todo todo) {
		return this.mapper.map(todo, TodoDTO.class);
	}
	
	@Autowired
	public TodoService(TodoRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	public TodoDTO create(Todo todo) {
		return this.mapToDTO(this.repo.save(todo));
	}
	
	public List<TodoDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public TodoDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(TodoNotFoundException::new));
	}
	
	public TodoDTO update(TodoDTO todoDTO, Long id) {
		Todo toUpdate = this.repo.findById(id).orElseThrow(TodoNotFoundException::new);
		toUpdate.setBody(todoDTO.getBody());
		SpringBeanUtil.mergeNotNull(todoDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
