package net.azurewebsites.soundsafeguard.ui

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
import net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer.AppBar
import net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer.AppDrawer
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
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(
                            route = navController.currentBackStackEntryAsState().value?.destination?.route ?: "home",
                            navigateToMain = { navController.navigate("main") },
                            navigateToSoundSettings = { navController.navigate("soundSetting") },
                            closeDrawer = { coroutineScope.launch { drawerState.close() } }
                        )
                    }
                ) {
                    Scaffold(
                        topBar = {
                            AppBar(title = "SSG", onMenuClick = { coroutineScope.launch { drawerState.open() }})
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
    }
}
