package net.azurewebsites.soundsafeguard.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import net.azurewebsites.soundsafeguard.presentation.service.DataSender
import net.azurewebsites.soundsafeguard.presentation.utils.NotificationUtils.createNotificationChannel
import net.azurewebsites.soundsafeguardwearos.presentation.theme.SoundSafeGuardTheme

class WearMainActivity : ComponentActivity() {

    private val dataSender = DataSender()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            SoundSafeGuardTheme {
//                RecordingScreen { inputString ->
//                    dataSender.sendDataToMobile(this, inputString)
//                }
                VoiceInputScreen(this)
            }
        }
    }
}