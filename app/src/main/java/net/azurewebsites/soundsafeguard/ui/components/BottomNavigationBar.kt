package net.azurewebsites.soundsafeguard.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    //navBar item
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Microphone,
        BottomNavigationItem.Setting
    )

    //navController의 현 루트
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //item foreach문을 통해 bottomNav 정의
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF00A980)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                unselectedContentColor = Color.Black,
                selectedContentColor = Color(0xFF00A980),
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = null,
                        tint = if (currentRoute == item.route) Color(0xFF00A980) else Color(
                            0xFF888888
                        )
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute == item.route) Color(0xFF00A980) else Color(
                            0xFF888888
                        )
                    )
                }
            )
        }
    }
}


sealed class BottomNavigationItem(
    val route: String, val icon: ImageVector, val title: String
) {
    object Home : BottomNavigationItem("main", Icons.Default.Home, "Main")
    object Microphone : BottomNavigationItem("customSoundSetting", Icons.Default.Mic, "CustomSound")
    object Setting : BottomNavigationItem("soundSetting", Icons.Default.Settings, "Settings")
}
