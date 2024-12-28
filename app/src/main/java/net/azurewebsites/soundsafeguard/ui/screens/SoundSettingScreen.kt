package net.azurewebsites.soundsafeguard.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.components.soundsetting.AddButton
import net.azurewebsites.soundsafeguard.ui.components.soundsetting.SoundList
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel

@Composable
fun SoundSettingScreen(
    navController: NavController,
    context: Context,
    viewModel: SoundViewModel
) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    var searchQuery by remember { mutableStateOf("") }
    val sounds = List(100) { "Siren$it" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        SoundSettingText()

        Text(
            text = "Selected",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.End),
        )

        // Selected Sound Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Not selected",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sounds
        Text(
            text = "Sounds",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.End)
        )

        SoundList(sounds, searchQuery) { newQuery ->
            searchQuery = newQuery
        }

        Spacer(modifier = Modifier.weight(1f))

        // Add Button
        AddButton(navController)
    }
}

@Composable
fun SoundSettingText() {
    // Title and Subtitle
    Text(
        text = "Sound Setting",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 43.4.dp, bottom = 10.dp)
    )
    Text(
        text = "Please select the sound\nyou want to receive alerts for.",
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 25.dp)
    )
}

@Preview
@Composable
fun SoundSettingPreview() {
    SoundSettingScreen(rememberNavController(), LocalContext.current, SoundViewModel())
}