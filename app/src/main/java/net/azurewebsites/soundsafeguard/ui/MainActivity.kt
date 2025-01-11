package net.azurewebsites.soundsafeguard.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.service.DataClientService
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

    private val dataService = DataClientService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        val viewModel = ViewModelProvider(
            this,
            SoundViewModelFactory(applicationContext)
        )[SoundViewModel::class.java]

        dataService.registerDataClient(this)

        setContent {
            val mainViewModel: MainViewModel = viewModel()

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
                            MainScreen(
                                viewModel = viewModel,
                                mainViewModel = mainViewModel
                            )
                        }
                        composable("soundSetting") {
                            SoundSettingScreen(
                                soundViewModel = viewModel,
                                mainViewModel
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

    override fun onDestroy() {
        super.onDestroy()
        dataService.unregisterDataClient(this)
    }

    private fun createNotificationChannel() {
        //API 26 이상부턴 Notification Channel을 생성해야함
        //R의 채널명이나 description은 별 제약이 없는듯?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("SSG_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            //채널 생성 메소드
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
