package net.azurewebsites.soundsafeguard.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundSafeGuardTheme {

            }
        }
    }
}