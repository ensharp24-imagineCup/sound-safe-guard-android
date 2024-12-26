package net.azurewebsites.soundsafeguard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundSafeGuardTheme {
                mainNavHost()
            }
        }
    }
}

@Composable
fun mainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = rememberNavController(),
        startDestination = "home"
    ) {
        composable("home") {
            // Home 화면
        }
    }
}