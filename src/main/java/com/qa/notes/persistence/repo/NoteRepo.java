package com.qa.notes.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.notes.persistence.domain.Note;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long>{

}
