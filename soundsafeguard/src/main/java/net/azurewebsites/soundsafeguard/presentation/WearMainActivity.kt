package net.azurewebsites.soundsafeguard.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import net.azurewebsites.soundsafeguard.presentation.NotificationUtils.createNotificationChannel
import net.azurewebsites.soundsafeguardwearos.presentation.theme.SoundSafeGuardTheme

class WearMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            SoundSafeGuardTheme {
                RecordingScreen { isRecording ->
                    handleRecordingState(isRecording)
                }
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