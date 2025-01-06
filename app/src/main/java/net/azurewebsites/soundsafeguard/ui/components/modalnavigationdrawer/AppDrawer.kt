package net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R

@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
    navigateToSoundSettings: () -> Unit,
    closeDrawer: () -> Unit
) {
    ModalDrawerSheet(modifier = modifier) {
        // 사용자 정보 섹션
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "User Name", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.inter_semibold)), lineHeight = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            NavigationDrawerItem(
                label = {
                    Text(text = "Main", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.inter_regular)), lineHeight = 18.sp, color = Color.Black)
                },
                selected = route == "my_information",
                onClick = {
                    navigateToMain()
                    closeDrawer()
                },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
            )

            NavigationDrawerItem(
                label = {
                    Text(text = "Settings", fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.inter_regular)), lineHeight = 18.sp, color = Color.Black)
                },
                selected = route == "settings",
                onClick = {
                    navigateToSoundSettings()
                    closeDrawer()
                },
                icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) }
            )


            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // 로그아웃 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(onClick = { /* 로그아웃 처리 로직 넣어야 함  */ }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout Icon", tint = Color(0xFFFF5E5E))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Logout", color = Color(0xFFFF5E5E))
            }
        }
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


