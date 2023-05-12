package com.example.notes.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.notes.NotificationWorker
import com.example.notes.domain.Note
import com.example.notes.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFragmentViewModel @Inject constructor(
    private val application: Application,
    private val addNoteUseCase: AddNoteUseCase
) : AndroidViewModel(application) {

    fun addNote(text: String) : Boolean {
        val item = Note(
            text = text
        )
        if (item.dateEnd != null) {
            WorkManager.getInstance(application).enqueue(
               NotificationWorker.makeRequest(
                   item.text,
                   item.id,
                   item.dateEnd
               )
            )
        }
        return addNoteUseCase(item)
    }
}