package com.example.moviebox

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val KEY_PUSH_TITLE = "title"
private const val KEY_PUSH_TEXT = "text"
private const val CHANNEL_ID = "channel"
private const val NOT_ID = 123

class MessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val messageData = message.data
        if (messageData.isNotEmpty()) {
            handleMessageData(messageData.toMap())
        }
    }

    private fun handleMessageData(data: Map<String, String>) {
        val title = data[KEY_PUSH_TITLE]
        val text = data[KEY_PUSH_TEXT]

        if (!title.isNullOrBlank() && !text.isNullOrBlank()) {
            showNotification(title, text)
        }
    }

    private fun showNotification(title: String, text: String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(text)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        notificationManager.notify(NOT_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val name = "Channel_1"
        val info = "About channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = info
        }
        notificationManager.createNotificationChannel(channel)
    }
}