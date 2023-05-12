package com.example.notes

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("NOTES_TAG", "HERE")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TOKEN", token)
    }
}