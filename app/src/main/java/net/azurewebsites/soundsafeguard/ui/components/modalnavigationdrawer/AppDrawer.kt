package net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Alignment

@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet(modifier = modifier) {
        DrawerHeader(modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(10.dp))

        NavigationDrawerItem(
            label = { Text(text = "Home", style = MaterialTheme.typography.labelSmall) },
            selected = route == "home",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )

        NavigationDrawerItem(
            label = { Text(text = "Settings", style = MaterialTheme.typography.labelSmall) },
            selected = route == "settings",
            onClick = {
                navigateToSettings()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) }
        )
    }
}

@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "My App",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.padding(bottom = 8.dp)
        )
    }
}
