package net.azurewebsites.soundsafeguard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.ui.components.soundsetting.AddButton
import net.azurewebsites.soundsafeguard.ui.components.soundsetting.SearchBar
import net.azurewebsites.soundsafeguard.ui.components.soundsetting.SoundList
import net.azurewebsites.soundsafeguard.viewmodel.SoundViewModel

@Composable
fun SoundSettingScreen(
    navController: NavController,
    viewModel: SoundViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var sounds by remember {
        mutableStateOf(List(100) { "Siren$it" })
    }
    var selectedSounds by remember {
        mutableStateOf(emptyList<String>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {

        SoundSettingText(
            modifier = Modifier.padding(top = 43.4.dp, bottom = 10.dp),
            subModifier = Modifier.padding(bottom = 25.dp)
        )

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
                .background(Color.White, RoundedCornerShape(10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (selectedSounds.isEmpty()) {
                Text(
                    text = "Not selected",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            } else {
                SoundList(
                    sounds = selectedSounds,
                    isSelected = true,
                    onSelect = {
                        selectedSounds = selectedSounds.toMutableList().apply { remove(it) }

                        sounds = sounds.toMutableList().apply { add(it) }
                        (sounds as MutableList<String>).sort()
                    },
                )
            }
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

        Column(
            modifier = Modifier
                .height(286.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(vertical = 15.dp),
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { newQuery ->
                    searchQuery = newQuery
                },
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            )

            SoundList(
                sounds = sounds,
                searchQuery = searchQuery,
                isSelected = false,
                onSelect = {
                    sounds = sounds.toMutableList().apply { remove(it) }

                    selectedSounds = selectedSounds.toMutableList().apply { add(it) }
                    (selectedSounds as MutableList<String>).sort()
                },
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Add Button
        AddButton(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp),
        )
    }
}

@Composable
fun SoundSettingText(
    modifier: Modifier = Modifier,
    subModifier: Modifier = Modifier
) {
    // Title and Subtitle
    Text(
        text = "Sound Setting",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
    Text(
        text = "Please select the sound\nyou want to receive alerts for.",
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = subModifier,
    )
}

//@Preview(showBackground = true)
//@Composable
//fun SoundSettingPreview() {
//    SoundSettingScreen(
//        navController = rememberNavController(),
//    )
//}