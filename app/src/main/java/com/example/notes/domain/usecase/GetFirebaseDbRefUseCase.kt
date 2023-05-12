package com.example.notes.domain.usecase

import com.example.notes.domain.NotesRepository
import javax.inject.Inject

class GetFirebaseDbRefUseCase @Inject constructor(private val repository: NotesRepository) {
    operator fun invoke() = repository.getFirebaseDbRef()
}