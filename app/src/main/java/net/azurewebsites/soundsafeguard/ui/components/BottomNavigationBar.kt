package net.azurewebsites.soundsafeguard.ui.components

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.R


@Composable
fun BottomNavigationBar(
    navController: NavController,
    ){

    //navBar item
    val items = listOf<BottomNavigationItem>(
        BottomNavigationItem.Home,
        BottomNavigationItem.Setting
    )

    //navController의 현 루트
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //item foreach문을 통해 bottomNav 정의
    BottomNavigation (
        backgroundColor = Color.White,
        contentColor = Color(0xFF00A980)

    ){
        items.forEach{ item ->
            BottomNavigationItem(

                selected = currentRoute == item.route,
                unselectedContentColor = Color.Black,
                selectedContentColor = Color(0xFF00A980),
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.route ){
                        popUpTo(navController.graph.startDestinationId){saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                icon = {
                    Icon(item.icon, contentDescription = null)
                },
                label = { Text(item.title)}
            )
        }
    }





}


sealed class BottomNavigationItem(
    val route: String, val icon: ImageVector, val title: String){
    object Home : BottomNavigationItem("main",Icons.Default.Home , "main")
    object Setting : BottomNavigationItem("soundSetting", Icons.Default.Settings, "Settings")
}