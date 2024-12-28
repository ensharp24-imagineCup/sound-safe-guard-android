package net.azurewebsites.soundsafeguard.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R

@Composable
fun MainScreen() {
    var isActivated by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (isActivated) Color(0xFFE0F7FA) else Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 아이콘 및 텍스트
            Image(
                painter = painterResource(id = if (isActivated) R.mipmap.app_icon_shadow else R.mipmap.app_icon_off),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            if (isActivated) {
                Text("Activated!", fontSize = 24.sp, color = Color(0xFF00796B))
            } else {
                Text("Start SSG", fontSize = 24.sp, color = Color(0xFF616161))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 스위치
            Switch(
                checked = isActivated,
                onCheckedChange = { isActivated = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 설명 텍스트
            if (isActivated) {
                Text("SSG listening to the sound.", fontSize = 16.sp, color = Color.Gray)
            } else {
                Text("Try the personalized sound alert service!", fontSize = 16.sp, color = Color.Gray)
            }
        }
    }
}