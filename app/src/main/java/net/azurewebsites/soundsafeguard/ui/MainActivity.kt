package net.azurewebsites.soundsafeguard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.screens.HomeScreen
import net.azurewebsites.soundsafeguard.ui.screens.MainScreen
import net.azurewebsites.soundsafeguard.ui.screens.SoundSettingScreen
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val viewModel = ViewModelProvider(
//            this,
//            MainViewModelFactory(applicationContext)
//        ).get(MainViewModel::class.java)

        setContent {
            SoundSafeGuardTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "soundSetting") {
                    composable("home") { HomeScreen(navController) }
                    composable("main") { MainScreen() }
//                    composable("soundSetting") {
//                        SoundSettingScreen(
//                            navController = navController,
//                            viewModel = viewModel,
//                        )
//                    }
                }
            }
        }
    }
}