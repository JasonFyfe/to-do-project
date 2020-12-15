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

import com.qa.notes.dto.NoteDTO;
import com.qa.notes.persistence.domain.Note;
import com.qa.notes.service.NoteService;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("dev")
class NoteControllerUnitTest
{
	
	@Autowired
	private NoteController controller;
	
	@MockBean
	private NoteService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private NoteDTO mapToDTO(Note note) {
		return this.mapper.map(note, NoteDTO.class);
	}
	
	private final Note TEST_NOTE_1 = new Note(1L, "Shopping List");
	private final Note TEST_NOTE_2 = new Note(2L, "Wish List");
	private final Note TEST_NOTE_3 = new Note(3L, "House Chores");
	
	private final List<Note> LISTOFNOTES = List.of(TEST_NOTE_1, TEST_NOTE_2, TEST_NOTE_3);

    @Test
    void createTest() throws Exception
    {
    	when(this.service.create(TEST_NOTE_1)).thenReturn(this.mapToDTO(TEST_NOTE_1));
    	
    	assertThat(new ResponseEntity<NoteDTO>(this.mapToDTO(TEST_NOTE_1), HttpStatus.CREATED))
    	.isEqualTo(this.controller.create(TEST_NOTE_1));
    	verify(this.service, atLeastOnce()).create(TEST_NOTE_1);
    }

    @Test
    void readAllTest() throws Exception
    {
    	List<NoteDTO> dtos = LISTOFNOTES.stream().map(this::mapToDTO).collect(Collectors.toList());
    	when(this.service.readAll()).thenReturn(dtos);
    	assertThat(this.controller.readAll()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
    	verify(this.service, atLeastOnce()).readAll();
    }

    @Test
    void readOneTest() throws Exception
    {
    	when(this.service.readOne(TEST_NOTE_1.getId())).thenReturn(this.mapToDTO(TEST_NOTE_1));
    	assertThat(new ResponseEntity<NoteDTO>(this.mapToDTO(TEST_NOTE_1), HttpStatus.OK))
    	.isEqualTo(this.controller.readOne(TEST_NOTE_1.getId()));
    	verify(this.service, atLeastOnce()).readOne(TEST_NOTE_1.getId());    	
    }

    @Test
    void updateTest() throws Exception
    {
    	when(this.service.update(this.mapToDTO(TEST_NOTE_1), TEST_NOTE_1.getId())).thenReturn(this.mapToDTO(TEST_NOTE_1));
    	assertThat(new ResponseEntity<NoteDTO>(this.mapToDTO(TEST_NOTE_1), HttpStatus.ACCEPTED))
    	.isEqualTo(this.controller.update(TEST_NOTE_1.getId(), this.mapToDTO(TEST_NOTE_1)));
    	verify(this.service, atLeastOnce()).update(this.mapToDTO(TEST_NOTE_1), TEST_NOTE_1.getId());
    }

    @Test
    void deleteTest() throws Exception
    {
    	when(this.service.delete(TEST_NOTE_1.getId())).thenReturn(false);
    	assertThat(this.controller.delete(TEST_NOTE_1.getId()))
    	.isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    	verify(this.service, atLeastOnce()).delete(TEST_NOTE_1.getId());
    }
    
    @Test
    void deleteTestBranch() throws Exception
    {
    	when(this.service.delete(TEST_NOTE_1.getId())).thenReturn(true);
    	assertThat(this.controller.delete(TEST_NOTE_1.getId()))
    	.isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    	verify(this.service, atLeastOnce()).delete(TEST_NOTE_1.getId());
    }
}