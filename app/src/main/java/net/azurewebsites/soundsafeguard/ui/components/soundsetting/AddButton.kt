package net.azurewebsites.soundsafeguard.ui.components.soundsetting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("home") },
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(
            text = "Add",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}