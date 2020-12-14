package com.qa.notes.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteDTO {

	private Long id;
	private String title;
	
	private List<TodoDTO> todos = new ArrayList<>();
}
