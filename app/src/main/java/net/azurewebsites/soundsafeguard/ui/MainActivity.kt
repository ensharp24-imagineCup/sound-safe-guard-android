package net.azurewebsites.soundsafeguard.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import net.azurewebsites.soundsafeguard.R
import net.azurewebsites.soundsafeguard.model.AssetManager
import net.azurewebsites.soundsafeguard.model.AudioRecoder
import net.azurewebsites.soundsafeguard.service.DataClientService
import net.azurewebsites.soundsafeguard.ui.components.BottomNavigationBar
import net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer.AppBar
import net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer.AppDrawer
import net.azurewebsites.soundsafeguard.ui.screens.CustomSoundScreen
import net.azurewebsites.soundsafeguard.ui.screens.MainScreen
import net.azurewebsites.soundsafeguard.ui.screens.RecordScreen
import net.azurewebsites.soundsafeguard.ui.screens.SoundSettingScreen
import net.azurewebsites.soundsafeguard.ui.screens.StartScreen
import net.azurewebsites.soundsafeguard.ui.theme.SoundSafeGuardTheme
import net.azurewebsites.soundsafeguard.viewmodel.MainViewModel
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModelFactory

class MainActivity : ComponentActivity() {


    var dataClientService:DataClientService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //AudioRecorder 생성 (녹음, 데이터 출력 등 )
        val audioRecorder =
            AudioRecoder(this, AssetManager().loadInterpreter(this, "yamnet.tflite"))
        dataClientService = DataClientService(this, audioRecorder)


        createNotificationChannel()

        val soundViewModel = ViewModelProvider(
            this,
            SoundViewModelFactory(applicationContext)
        )[SoundViewModel::class.java]

        dataClientService!!.registerDataClient()

        setContent {
            val mainViewModel: MainViewModel = viewModel()

            SoundSafeGuardTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(
                            route = navController.currentBackStackEntryAsState().value?.destination?.route
                                ?: "home",
                            navigateToMain = { navController.navigate("main") },
                            navigateToSoundSettings = { navController.navigate("soundSetting") },
                            closeDrawer = { coroutineScope.launch { drawerState.close() } }
                        )
                    }
                ) {
                    Scaffold(
                        topBar = {
                            AppBar(
                                title = "SSG",
                                onMenuClick = { coroutineScope.launch { drawerState.open() } })
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
                                    viewModel = soundViewModel,
                                    mainViewModel = mainViewModel,
                                    dataClientService = dataClientService!!,
                                    audioRecoder = audioRecorder

                                )
                            }
                            composable("soundSetting") {
                                SoundSettingScreen(
                                    soundViewModel = soundViewModel,
                                    mainViewModel = mainViewModel
                                )
                            }
                            composable("record") {
                                RecordScreen()
                            }
                            composable("customSoundSetting") {
                                CustomSoundScreen(
                                    navController = navController,
                                    viewModel = soundViewModel,
                                    mainViewModel = mainViewModel,
                                    audioRecorder = audioRecorder
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataClientService?.unregisterDataClient()
    }

    private fun createNotificationChannel() {
        //API 26 이상부턴 Notification Channel을 생성해야함
        //R의 채널명이나 description은 별 제약이 없는듯?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
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