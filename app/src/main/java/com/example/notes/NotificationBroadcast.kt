package com.example.notes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.notes.ui.MainActivity




class NotificationBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTE_ID, 1)
        val textContent = intent.getStringExtra(TEXT_NOTE) ?: ""
        val startActivityPendingIntent = createActivityPendingIntent(context.applicationContext)

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
            .setContentIntent(startActivityPendingIntent)
            .setAutoCancel(true)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private fun createActivityPendingIntent(context: Context) : PendingIntent {
        val resultIntent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context, 0, resultIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val NOTE_ID = "note_id"
        const val TEXT_NOTE = "text_note"
        private const val NOTIFICATION_TITLE = "New note"
        private const val CHANNEL_NAME = "new notes"
        private const val CHANNEL_ID = "note"
    }
}