package net.azurewebsites.soundsafeguard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.screens.SoundSettingScreen
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundSafeGuardTheme {
                MainNavHost()
            }
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "soundSetting"
    ) {
        composable("soundSetting") {
            // Home 화면
            SoundSettingScreen(navController, LocalContext.current)
        }
    }
}