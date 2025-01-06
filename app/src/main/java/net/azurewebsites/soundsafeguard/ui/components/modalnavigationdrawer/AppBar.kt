package net.azurewebsites.soundsafeguard.ui.components.modalnavigationdrawer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import net.azurewebsites.soundsafeguard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, onMenuClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon_shadow),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color(0xFF00A980),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 70.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "메뉴")
            }
        }
    )
}

/*
@Preview
@Composable
fun AppBarPreview() {
    AppBar(title = "SSG")
}
*/