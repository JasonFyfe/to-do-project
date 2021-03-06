package com.qa.notes.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Note with that ID does not exist. Please try again.")

public class NoteNotFoundException extends EntityNotFoundException{
	private static final long serialVersionUID = 1L;
}
