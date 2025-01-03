package net.azurewebsites.soundsafeguard.ui.components.test

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme

class MainActivity : ComponentActivity() {

    private lateinit var notificationViewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationViewModel = NotificationViewModel(applicationContext)

        setContent {
            SoundSafeGuardTheme {
                NotificationDemo(
                    onSendNotification = {
                        notificationViewModel.sendNotification()
                    }
                )
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        notificationViewModel.notificationPermissionEvent.observe(this) { shouldRequest ->
            if (shouldRequest) {
                checkNotificationPermission()
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NotificationViewModel.NOTIFICATION_PERMISSION_REQUEST_CODE
            )
        }
    }
}

@Composable
fun NotificationDemo(onSendNotification: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Button(onClick = onSendNotification) {
            Text("Send Notification")
        }
    }
}


// 기존에 MainActivity 한곳에 박아놨던 알림 예시 코드
// 위의 view와 viewmodel을 분리한 로직과 동일함

//private fun createNotificationChannel() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "Example Channel"
//        val descriptionText = "This is a channel for example notifications"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//}
//
//private fun sendNotification() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // 권한이 없으므로 사용자에게 알림 권한 요청
//            checkNotificationPermission()
//            return
//        }
//    }
//
//    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//        .setSmallIcon(android.R.drawable.ic_dialog_info)
//        .setContentTitle("Example Notification")
//        .setContentText("This is a test notification.")
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//    with(NotificationManagerCompat.from(this)) {
//        notify(NOTIFICATION_ID, builder.build())
//    }
//}
//
//companion object {
//    private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1
//}
//private fun checkNotificationPermission() {
//    // 권한 요청
//    ActivityCompat.requestPermissions(
//        this,
//        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//        NOTIFICATION_PERMISSION_REQUEST_CODE
//    )
//}