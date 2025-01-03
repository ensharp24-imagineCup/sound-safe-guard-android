package net.azurewebsites.soundsafeguard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.components.AppBar
import net.azurewebsites.soundsafeguard.ui.components.BottomNavigationBar
import net.azurewebsites.soundsafeguard.ui.screens.MainScreen
import net.azurewebsites.soundsafeguard.ui.screens.RecordScreen
import net.azurewebsites.soundsafeguard.ui.screens.SoundSettingScreen
import net.azurewebsites.soundsafeguard.ui.screens.StartScreen
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme
import net.azurewebsites.soundsafeguard.viewmodel.MainViewModel
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
            val mainViewModel : MainViewModel = viewModel()

            SoundSafeGuardTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        AppBar(title = "SSG")
                    },
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = "main",
                        Modifier.padding(innerPadding)
                    ) {

                        composable("start") {
                            StartScreen()
                        }
                        composable("main") {
                            MainScreen(mainViewModel)
                        }
                        composable("soundSetting") {
                            SoundSettingScreen(
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                        composable("record") {
                            RecordScreen()
                        }
                    }
                }

            }
        }
    }
}
