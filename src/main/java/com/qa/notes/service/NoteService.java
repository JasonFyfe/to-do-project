package com.qa.notes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.notes.dto.NoteDTO;
import com.qa.notes.exceptions.NoteNotFoundException;
import com.qa.notes.persistence.domain.Note;
import com.qa.notes.persistence.repo.NoteRepo;
import com.qa.notes.util.SpringBeanUtil;

@Service
public class NoteService {
	
	private NoteRepo repo;
	
	private ModelMapper mapper;
	
	private NoteDTO mapToDTO(Note note) {
		return this.mapper.map(note, NoteDTO.class);
	}
	
	@Autowired
	public NoteService(NoteRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	public NoteDTO create(Note note) {
		return this.mapToDTO(this.repo.save(note));
	}
	
	public List<NoteDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public NoteDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(NoteNotFoundException::new));
	}
	
	public NoteDTO update(NoteDTO noteDTO, Long id) {
		Note toUpdate = this.repo.findById(id).orElseThrow(NoteNotFoundException::new);
		toUpdate.setTitle(noteDTO.getTitle());
		SpringBeanUtil.mergeNotNull(noteDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
