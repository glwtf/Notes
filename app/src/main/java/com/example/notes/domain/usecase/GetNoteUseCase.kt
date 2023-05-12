package com.example.notes.domain.usecase

import com.example.notes.domain.NotesRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    operator fun invoke(noteId: Int) = repository.getNote(noteId)
}