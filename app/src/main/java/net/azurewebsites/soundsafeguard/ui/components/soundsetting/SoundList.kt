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
fun SoundList(
    sounds: List<String>,
    searchQuery: String,
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 15.dp)
                .fillMaxWidth()
                .height(40.dp)
        )

        // List of sounds
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            sounds.forEach { sound ->
                if (searchQuery.isEmpty() || searchQuery.lowercase() in sound.lowercase())
                SoundItem(
                    soundName = sound,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .background(Color.White)
                        .height(51.dp),
                ) {
                }
            }
        }
    }
}

@Composable
fun SoundItem(
    soundName: String,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onSelect() }
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