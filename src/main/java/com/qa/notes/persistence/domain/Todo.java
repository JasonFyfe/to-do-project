package com.qa.notes.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String body;
	
	@ManyToOne
	private Note note;
	
	public Todo(Long id, String body) {
		super();
		this.id = id;
		this.body = body;
	}
	
	public Todo(String body) {
		super();
		this.body = body;
	}

}
