package net.azurewebsites.soundsafeguard.ui.components.test

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel(private val context: Context) : ViewModel() {
    private val CHANNEL_ID = "example_channel"
    private val NOTIFICATION_ID = 1
    private val _notificationPermissionEvent = MutableLiveData<Boolean>()
    val notificationPermissionEvent: LiveData<Boolean> = _notificationPermissionEvent

    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1
    }

    init {
        createNotificationChannel()
    }

    fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 권한 요청 이벤트 발생
                _notificationPermissionEvent.postValue(true)
                return
            }
        }
        sendNotificationInternal()
    }

    private fun sendNotificationInternal() {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Example Notification")
            .setContentText("This is a test notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val name = "Example Channel"
        val descriptionText = "This is a channel for example notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}