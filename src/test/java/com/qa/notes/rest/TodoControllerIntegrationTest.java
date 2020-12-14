package com.qa.notes.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.notes.dto.TodoDTO;
import com.qa.notes.persistence.domain.Todo;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:todo-schema.sql", "classpath:todo-data.sql"},
	 executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
class TodoControllerIntegrationTest
{
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	@Autowired
	private ModelMapper mapper;
	
	private TodoDTO mapToDTO(Todo todo) {
		return this.mapper.map(todo, TodoDTO.class);
	}
	
	private final Todo TEST_TODO_1 = new Todo(1L, "Bread");
	private final Todo TEST_TODO_2 = new Todo(2L, "Eggs");
	private final Todo TEST_TODO_3 = new Todo(3L, "Milk");
	
	private final List<Todo> LISTOFTODOS = List.of(TEST_TODO_1, TEST_TODO_2, TEST_TODO_3);
	
	private final String URI = "/todos";
	
	@Test
	void createTest() throws Exception {
		TodoDTO testDTO = mapToDTO(new Todo("Flour"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);
		
		RequestBuilder request = post(URI).contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		
		ResultMatcher checkStatus = status().isCreated();
		
		TodoDTO testSavedDTO = mapToDTO(new Todo("Flour"));
		testSavedDTO.setId(13L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
		
		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
	
}