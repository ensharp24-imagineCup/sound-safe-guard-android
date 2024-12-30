package net.azurewebsites.soundsafeguard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.screens.MainScreen
import net.azurewebsites.soundsafeguard.ui.screens.SoundSettingScreen
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            SoundViewModelFactory(applicationContext)
        )[SoundViewModel::class.java]

        setContent {
            SoundSafeGuardTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "soundSetting") {
                    composable("main") { MainScreen() }
                    composable("soundSetting") {
                        SoundSettingScreen(
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
}