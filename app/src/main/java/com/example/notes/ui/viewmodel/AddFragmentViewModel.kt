package com.example.notes.ui.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.example.notes.NotificationBroadcast
import com.example.notes.domain.Note
import com.example.notes.domain.usecase.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFragmentViewModel @Inject constructor(
    private val application: Application,
    private val addNoteUseCase: AddNoteUseCase
) : AndroidViewModel(application) {

    fun addNote(text: String, planTimeMillis: Long?) {
        val item = Note(
            text = text,
            planTimeMillis = planTimeMillis
        )
        addNoteUseCase(item)
        planTimeMillis?.let {
            scheduleNotification(item)
        }
    }

    private fun scheduleNotification(item: Note) {
        val intent = Intent(application, NotificationBroadcast::class.java)
        val noteId = item.id?.toByteArray()?.sum() ?: 1
        intent.putExtra(NotificationBroadcast.TEXT_NOTE, item.text)
        intent.putExtra(NotificationBroadcast.NOTE_ID, noteId)

        val pendingIntent = PendingIntent.getBroadcast(
            application,
            noteId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.planTimeMillis!!,
            pendingIntent
        )
    }
}