package com.qa.notes.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	
	public Note(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public Note(String title) {
		super();
		this.title = title;
	}
}
