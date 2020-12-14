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
import com.qa.notes.dto.NoteDTO;
import com.qa.notes.persistence.domain.Note;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:todo-schema.sql", "classpath:todo-data.sql"},
	 executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "dev")
class NoteControllerIntegrationTest
{
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	@Autowired
	private ModelMapper mapper;
	
	private NoteDTO mapToDTO(Note note) {
		return this.mapper.map(note, NoteDTO.class);
	}

	private final Note TEST_NOTE_1 = new Note(1L, "Shopping List");
	private final Note TEST_NOTE_2 = new Note(2L, "Wish List");
	private final Note TEST_NOTE_3 = new Note(3L, "House Chores");
	
	private final List<Note> LISTOFNOTES = List.of(TEST_NOTE_1, TEST_NOTE_2, TEST_NOTE_3);
	
	private final String URI = "/notes";
	
	@Test
	void createTest() throws Exception {
		NoteDTO testDTO = mapToDTO(new Note("Movies List"));
		String testDTOAsJSON = this.jsonifier.writeValueAsString(testDTO);
		
		RequestBuilder request = post(URI).contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		
		ResultMatcher checkStatus = status().isCreated();
		
		NoteDTO testSavedDTO = mapToDTO(new Note("Movies List"));
		testSavedDTO.setId(4L);
		String testSavedDTOAsJSON = this.jsonifier.writeValueAsString(testSavedDTO);
		
		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);
		
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
}