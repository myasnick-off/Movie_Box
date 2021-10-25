package com.example.moviebox

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.lang.StringBuilder

// BroadcastReceiver для получения сообщений от системы о статусе интернет-подключения
class ConnectivityBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = StringBuilder().apply {
            append("SYSTEM MESSAGE:\n")
            append("${intent?.action}")
        }
        intent?.let { Toast.makeText(context, message, Toast.LENGTH_SHORT).show()}
    }
}