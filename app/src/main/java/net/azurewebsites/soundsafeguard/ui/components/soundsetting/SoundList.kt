package net.azurewebsites.soundsafeguard.ui.components.soundsetting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azurewebsites.soundsafeguard.R

@Composable
fun SoundList(sounds: List<String>, searchedName: String, onSearchQueryChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .height(286.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 15.dp)
    ) {
        SearchBar(searchedName, onSearchQueryChanged)

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