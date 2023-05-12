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

    fun addNote(text: String, date: String, time: String) : Boolean {
        val dateEnd = if ("Date" in date) null else date
        val timeEnd = if ("Time" in time) null else time
        val item = Note(
            text = text,
            dateEnd = dateEnd,
            timeEnd = timeEnd
        )
        if (item.dateEnd != null && item.timeEnd != null) {
            WorkManager.getInstance(application).enqueue(
               NotificationWorker.makeRequest(
                   item.text,
                   item.id,
                   item.dateEnd!!,
                   item.timeEnd!!,
               )
            )
        }
        return addNoteUseCase(item)
    }
}