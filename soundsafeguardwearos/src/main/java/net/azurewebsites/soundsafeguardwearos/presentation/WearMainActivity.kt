package net.azurewebsites.soundsafeguardwearos.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import net.azurewebsites.soundsafeguardwearos.R
import net.azurewebsites.soundsafeguardwearos.presentation.theme.SoundSafeGuardTheme

class WearMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            SoundSafeGuardTheme {
                RecordingScreen { isRecording ->
                    handleRecordingState(isRecording)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("SSG_CHANNEL", name, importance).apply {
                description = descriptionText
            }

            with(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager) {
                createNotificationChannel(channel)
            }
        }
    }

    private fun handleRecordingState(isRecording: Boolean) {
        val serviceIntent = Intent(this, SoundCaptureService::class.java)
        if (isRecording) {
            serviceIntent.putExtra("command", "startRecording")
            serviceIntent.putExtra("string_input", "Recording Started")
            serviceIntent.putExtra("int_input", 123)
            startForegroundService(serviceIntent)
        } else {
            stopService(serviceIntent)
        }
    }
}