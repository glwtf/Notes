package com.example.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notes.domain.Note
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.GetFirebaseDbRefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getFirebaseDbRefUseCase: GetFirebaseDbRefUseCase
) : ViewModel() {

    fun deleteNote(noteItem: Note) = deleteNoteUseCase(noteItem)

    fun getFirebaseDbRef() = getFirebaseDbRefUseCase()

}