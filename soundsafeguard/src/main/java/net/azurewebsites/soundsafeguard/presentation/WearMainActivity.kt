package net.azurewebsites.soundsafeguard.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import net.azurewebsites.soundsafeguard.presentation.NotificationUtils.createNotificationChannel
import net.azurewebsites.soundsafeguardwearos.presentation.theme.SoundSafeGuardTheme

class WearMainActivity : ComponentActivity() {

    private val soundCaptureService = SoundCaptureService()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

        soundCaptureService.registerDataClient(this)

        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            SoundSafeGuardTheme {
                RecordingScreen { isRecording ->
                    if (isRecording) {
                        soundCaptureService.sendDataToMobile(this)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundCaptureService.unregisterDataClient(this)
    }
}