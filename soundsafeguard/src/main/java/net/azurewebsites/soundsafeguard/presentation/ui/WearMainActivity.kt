package net.azurewebsites.soundsafeguard.presentation.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.wearable.Wearable
import net.azurewebsites.soundsafeguard.presentation.service.AudioRecorderManager
import net.azurewebsites.soundsafeguard.presentation.service.DataSender
import net.azurewebsites.soundsafeguard.presentation.service.PermissionHandler
import net.azurewebsites.soundsafeguard.presentation.utils.NotificationUtils.createNotificationChannel
import net.azurewebsites.soundsafeguardwearos.presentation.theme.SoundSafeGuardTheme

class WearMainActivity : ComponentActivity() {

    private val dataSender = DataSender(Wearable.getDataClient(this))
    private val audioRecorderManager = AudioRecorderManager(this, dataSender)
    private val permissionHandler = PermissionHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Notification Channel 생성
        createNotificationChannel(this)

        // 권한 처리
        val requestPermissionLauncher = permissionHandler.createPermissionLauncher()
        if (!permissionHandler.isAudioPermissionGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            SoundSafeGuardTheme {
                AudioRecorderScreen(
                    onStartRecording = { audioRecorderManager.startRecording() },
                    onStopRecording = { audioRecorderManager.stopRecording() }
                )
            }
        }
    }
}