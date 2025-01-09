package net.azurewebsites.soundsafeguardwearos.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationUtils {

    fun createNotificationChannel(
        context: Context,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "SSG_CHANNEL",
                "SSG_CHANNEL",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "SSG_TEST"
            }

            with(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager) {
                createNotificationChannel(channel)
            }
        }
    }
}