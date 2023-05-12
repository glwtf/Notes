package com.example.notes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.notes.domain.Note
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


class NotificationWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val textNote = workerParams.inputData.getString(TEXT_NOTE) ?: ""
        val noteId = workerParams.inputData.getString(NOTE_ID) ?: ""
        if (noteId != null || textNote != "") {
            showNotification(textNote, noteId)
        }
        return Result.success()
    }

    private fun showNotification(textContent: String, noteId: String) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager: NotificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_info_24)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify(noteId.toInt(), notification)
    }

    companion object {
        private const val TEXT_NOTE = "text_note"
        private const val NOTE_ID = "note_id"
        private const val NOTIFICATION_TITLE = "New note"
        private const val CHANNEL_NAME = "new notes"
        private const val CHANNEL_ID = "note"

        private fun getDuration(date: String, time: String): Long {
            val dateList = date.split(".").map {
                it.toInt()
            }
            val timeList = time.split(":").map {
                it.toInt()
            }
            val noteDateCalendar = Calendar.getInstance()
            noteDateCalendar.set(
                dateList[2], dateList[1]-1, dateList[0]
            )
            noteDateCalendar.set(Calendar.HOUR, timeList[0])
            noteDateCalendar.set(Calendar.MINUTE, timeList[1])
            return noteDateCalendar.timeInMillis - System.currentTimeMillis()
        }
        fun makeRequest(
            textNote: String?,
            noteId: String?,
            date: String,
            time: String
        ): OneTimeWorkRequest {
            val duration = getDuration(date, time)
            return OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(workDataOf(
                    TEXT_NOTE to textNote,
                    NOTE_ID to noteId
                ))
                .setInitialDelay(duration, TimeUnit.MILLISECONDS)
                .build()
        }
    }
}