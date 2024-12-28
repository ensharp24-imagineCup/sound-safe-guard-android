package net.azurewebsites.soundsafeguard.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import net.azurewebsites.soundsafeguard.R

@Composable
fun SoundSettingScreen(
    navController: NavController,
    context: Context
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

        Column(
            modifier = Modifier
                .height(286.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(vertical = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                // search
                SearchComposable(searchQuery) { newValue ->
                    searchQuery = newValue
                }

                Spacer(modifier = Modifier.weight(1f))

                // category
                CategoryComposable()
            }

            // List of sounds
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                sounds.forEach { sound ->
                    SoundItem(soundName = sound) {

                    }
                }
            }
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

@Composable
fun SearchComposable(searchedName: String, onSearchQueryChanged: (String) -> Unit) {
    BasicTextField(
        value = searchedName,
        onValueChange = { newValue ->
            onSearchQueryChanged(newValue)
        },
        singleLine = true,
        modifier = Modifier
            .background(color = Color(0xFFEAEAEA), shape = RoundedCornerShape(12.dp))
            .width(172.dp)
            .fillMaxHeight(),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                if (searchedName.isEmpty()) {
                    Text(
                        text = "Name",
                        fontSize = 16.sp,
                        color = Color(0xFF888888)
                    )
                }

                innerTextField()
            }
        }
    )
}

@Composable
fun CategoryComposable() {
    Row(
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(12.dp))
            .fillMaxHeight()
            .width(120.dp)
            .padding(11.dp)
    ) {
        Text(
            text = "Category",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.category_selected_icon),
            contentDescription = "Siren Icon",
            modifier = Modifier
                .size(13.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun SoundItem(soundName: String, onSelect: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(Color.White)
            .clickable { onSelect() }
            .height(51.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.siren_icon),
                contentDescription = "Siren Icon",
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Sound name
            Text(
                text = soundName,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )

            // Add button
            IconButton(onClick = onSelect) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                )
            }
        }
        HorizontalDivider(thickness = 1.dp)
    }
}

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

@Preview
@Composable
fun SoundSettingPreview() {
    SoundSettingScreen(rememberNavController(), LocalContext.current)
}