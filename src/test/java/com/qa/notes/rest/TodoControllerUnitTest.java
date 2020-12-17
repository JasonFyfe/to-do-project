package com.qa.notes.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.notes.dto.TodoDTO;
import com.qa.notes.persistence.domain.Todo;
import com.qa.notes.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("dev")
class TodoControllerUnitTest
{
	@Autowired
	private TodoController controller;
	
	@MockBean
	private TodoService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private TodoDTO mapToDTO(Todo todo) {
		return this.mapper.map(todo, TodoDTO.class);
	}
	
	private final Todo TEST_TODO_1 = new Todo(1L, "Eggs");
	private final Todo TEST_TODO_2 = new Todo(2L, "Bread");
	private final Todo TEST_TODO_3 = new Todo(3L, "Milk");
	
	private final List<Todo> LISTOFTODOS = List.of(TEST_TODO_1, TEST_TODO_2, TEST_TODO_3);

    @Test
    void createTest() throws Exception
    {
    	when(this.service.create(TEST_TODO_1)).thenReturn(this.mapToDTO(TEST_TODO_1));
    	assertThat(new ResponseEntity<TodoDTO>(this.mapToDTO(TEST_TODO_1), HttpStatus.CREATED))
    	.isEqualTo(this.controller.create(TEST_TODO_1));
    	verify(this.service, atLeastOnce()).create(TEST_TODO_1);
    }

    @Test
    void readAllTest() throws Exception
    {
    	List<TodoDTO> dtos = LISTOFTODOS.stream().map(this::mapToDTO).collect(Collectors.toList());
    	when(this.service.readAll()).thenReturn(dtos);
    	assertThat(this.controller.readAll()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
    	verify(this.service, atLeastOnce()).readAll();
    }

    @Test
    void readOneTest() throws Exception
    {
    	when(this.service.readOne(TEST_TODO_1.getId())).thenReturn(this.mapToDTO(TEST_TODO_1));
    	assertThat(new ResponseEntity<TodoDTO>(this.mapToDTO(TEST_TODO_1), HttpStatus.OK))
    	.isEqualTo(this.controller.readOne(TEST_TODO_1.getId()));
    	verify(this.service, atLeastOnce()).readOne(TEST_TODO_1.getId());
    }

    @Test
    void updateTest() throws Exception
    {
    	when(this.service.update(this.mapToDTO(TEST_TODO_1), TEST_TODO_1.getId())).thenReturn(this.mapToDTO(TEST_TODO_1));
    	assertThat(new ResponseEntity<TodoDTO>(this.mapToDTO(TEST_TODO_1), HttpStatus.ACCEPTED))
    	.isEqualTo(this.controller.update(TEST_TODO_1.getId(), this.mapToDTO(TEST_TODO_1)));
    	verify(this.service, atLeastOnce()).update(this.mapToDTO(TEST_TODO_1), TEST_TODO_1.getId());
    }

    @Test
    void deleteTest() throws Exception
    {
    	when(this.service.delete(TEST_TODO_1.getId())).thenReturn(false);
    	assertThat(this.controller.delete(TEST_TODO_1.getId()))
    	.isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    	verify(this.service, atLeastOnce()).delete(TEST_TODO_1.getId());
    }
    
    @Test
    void deleteTestBranch() throws Exception
    {
    	when(this.service.delete(TEST_TODO_1.getId())).thenReturn(true);
    	assertThat(this.controller.delete(TEST_TODO_1.getId()))
    	.isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    	verify(this.service, atLeastOnce()).delete(TEST_TODO_1.getId());
    }
}