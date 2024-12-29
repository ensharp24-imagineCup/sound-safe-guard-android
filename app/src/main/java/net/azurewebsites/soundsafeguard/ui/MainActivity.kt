    package net.azurewebsites.soundsafeguard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.components.AppBar
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme
import net.azurewebsites.soundsafeguard.ui.screens.MainScreen
import net.azurewebsites.soundsafeguard.ui.screens.HomeScreen
import net.azurewebsites.soundsafeguard.ui.screens.SoundSettingScreen
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel
import androidx.compose.material3.Scaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundSafeGuardTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        AppBar(title = "SSG")
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = "main", Modifier.padding(innerPadding)) {
                        composable("home") { 
                            HomeScreen(navController) 
                        }
                        composable("main") { 
                            MainScreen() 
                        }
                        composable("soundSetting") { 
                            SoundSettingScreen(navController, LocalContext.current, SoundViewModel()) 
                        }
                    }
                }
            }
        }
    }
}