package com.example.notes.domain.usecase

import com.example.notes.domain.Note
import com.example.notes.domain.NotesRepository
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(private val repository: NotesRepository) {

    operator fun invoke(noteItem: Note) = repository.editNote(noteItem)
}